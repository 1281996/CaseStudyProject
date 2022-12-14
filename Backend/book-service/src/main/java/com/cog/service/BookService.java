package com.cog.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cog.dto.BookDto;
import com.cog.dto.BookResDto;
import com.cog.dto.BuyDto;
import com.cog.dto.ResponseDto;
import com.cog.entity.Book;
import com.cog.entity.Payment;
import com.cog.entity.Role;
import com.cog.entity.User;

import com.cog.enums.Category;
import com.cog.enums.Event;
import com.cog.enums.PaymentStatus;
import com.cog.repository.BookRepository;

import com.cog.repository.PaymentRepository;
import com.cog.util.Constant;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class BookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
	@Autowired
	BookRepository bookRepository;
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
	@Autowired
	UserMappingService userMappingService;
	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	RestTemplate restTemplate;

	public BookResDto saveBook(BookDto bookDto, Integer authorId) {
		LOGGER.info("save book");
		Book book = new Book();
		setBook(bookDto, authorId, book);
		Book bookRes = bookRepository.save(book);
		BookResDto bookResDto = new BookResDto();
		bookResDto.setBookDto(getAllMyBooks());
		if (bookRes != null) {
			bookResDto.setResponse(Constant.UPDATED_SUCCESS_MSG);
		} else {
			bookResDto.setResponse(Constant.BOOK_MSG_FALIURE);
		}
		return bookResDto;
	}

	private Book setBook(BookDto bookDto, Integer authorId, Book book) {
		book.setCategory(bookDto.getCategory());
		book.setContent(bookDto.getContent());
		book.setImage(bookDto.getImage());
		book.setPrice(bookDto.getPrice());
		book.setPublisher(bookDto.getPublisher());
		book.setTitle(bookDto.getTitle());
		Role role = roleService.findById(Constant.ROLE_AUTHOR_ID);
		book.setRole(role);
		User user = userService.findByUserId(authorId);
		book.setUser(user);
		book.setStatus(bookDto.getStatus());
		book.setPublishedDate(LocalDate.now());
		return book;
	}

	public BookResDto editBook(BookDto bookDto, Integer authorId, Integer bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new NullPointerException("Book Not Found with BookId: " + bookId));
		setBook(bookDto, authorId, book);
		bookRepository.save(book);
		BookResDto bookResDto = new BookResDto();
		bookResDto.setBookDto(getAllMyBooks());
		bookResDto.setResponse(Constant.UPDATED_SUCCESS_MSG);
		return bookResDto;
	}

	public List<BookDto> getAllMyBooks() {
		List<Book> books = bookRepository.findAll();
		return setBookData(books);
	}

	public List<BookDto> getAllMyBooksByAuthorId(Integer authorId) {
		List<Book> books = bookRepository.findByUserId(authorId);
		return setBookData(books);
	}

	private List<BookDto> setBookData(List<Book> books) {
		List<BookDto> bookData = new ArrayList<>();
		books.forEach(book -> {
			BookDto dto = new BookDto();
			dto.setCategory(book.getCategory());
			dto.setContent(book.getContent());
			dto.setId(book.getId());
			dto.setImage(book.getImage());
			dto.setPrice(book.getPrice());
			dto.setPublisher(book.getPublisher());
			dto.setPublishedDate(book.getPublishedDate());
			dto.setRole(book.getRole());
			dto.setStatus(book.getStatus());
			dto.setTitle(book.getTitle());
			dto.setUser(book.getUser());
			bookData.add(dto);
		});
		return bookData;
	}

	public List<BookDto> getReaderBooks() {
		List<Book> books = bookRepository.findByStatus(Event.UNBLOCK);
		return setBookData(books);
	}

	public List<BookDto> getFilteredBooks(Category category, Integer author, BigDecimal price, String publisher) {
		User user = userService.findByUserId(author);
		List<Book> books = bookRepository.findByCategoryUserIdPublisherPrice(category.name(), user.getId(), publisher,
				price);
		LOGGER.info(category.name() + "-" + user.getId() + "-" + publisher + "-" + price);
		LOGGER.info(books.size() + "size");
		return setBookData(books);
	}

	public List<String> getDistinctPublisherList() {
		return bookRepository.findDistinctPublishers();
	}

	public ResponseDto buyBook(BuyDto buyDto) {
		LOGGER.info("buyBookservce");
		// checking balnace is sufficient or not
		String uri = "http://localhost:8081/card/" + buyDto.getCardNumber();
		LOGGER.info(uri);
		BuyDto cardDetails = restTemplate.getForObject(uri, BuyDto.class);
		ResponseDto responseDto = new ResponseDto();
		if (cardDetails != null) {
			responseDto = isBalanceSufficient(buyDto, cardDetails, responseDto);
			if (!responseDto.isFlag()) {

				return responseDto;
			}
		} else {
			responseDto.setResponse("Incorrect Card Number");
			return responseDto;
		}

		LOGGER.info(cardDetails.getAmount() + "amount");
		// checking book is purchased already and saving data into payment
		buyDto.getBooks().forEach(book -> {

			Payment paymentRes = paymentRepository.findByEmailAndBookId(buyDto.getEmail(), book.getId());

			if (paymentRes == null) {
				Book bookRes = bookRepository.findById(book.getId()).get();
				Payment payment = new Payment();
				payment.setBook(bookRes);
				payment.setPaymentDate(LocalDateTime.now());
				payment.setPaymentType(Constant.CARD);
				payment.setCardNumber(buyDto.getCardNumber());
				payment.setEmail(buyDto.getEmail());
				payment.setName(buyDto.getName());
				payment.setPaymentStatus(PaymentStatus.SUCCESS);
				paymentRepository.save(payment);
			}
		});
		// debit amount in to card
		String uriUpdate = "https://4d2vz23n9h.execute-api.us-east-1.amazonaws.com/prod/" + buyDto.getCardNumber() + "/" + buyDto.getAmount();
		HttpEntity<BuyDto> entity = new HttpEntity<BuyDto>(buyDto);
		LOGGER.info("buyDto.getAmount()"+buyDto.getAmount());
		ResponseEntity<ResponseDto> dto = restTemplate.exchange(uriUpdate, HttpMethod.PUT, entity, ResponseDto.class);
		responseDto.setResponse(dto.getBody().getResponse());
		return responseDto;
	}

	private ResponseDto isBalanceSufficient(BuyDto buyDto, BuyDto bankDeatils, ResponseDto responseDto) {

		if (buyDto != null) {
			if (bankDeatils.getAmount() >= buyDto.getAmount()) {
				responseDto.setFlag(true);
			}
		}
		responseDto.setResponse("Insufficient Balance");
		return responseDto;
	}

	public List<Payment> getPurchasedBooks(String emailId) {
		List<Payment> payments = paymentRepository.findByEmail(emailId);
		payments.forEach(payment -> {

			long hours = ChronoUnit.HOURS.between(payment.getPaymentDate(), LocalDateTime.now());
			System.out.println(hours);
			if (hours <= 24) {
				payment.setRefundStatus(true);
			}

		});
		return payments;
	}

	public ByteArrayInputStream getBookContent(String emailId, Integer bookId)
			throws FileNotFoundException, DocumentException {
		Payment paymentRes = paymentRepository.findByEmailAndBookId(emailId, bookId);
		return createPdfDocument(paymentRes);

	}

	private ByteArrayInputStream createPdfDocument(Payment payment) throws FileNotFoundException, DocumentException {
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, outputStream);
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk(payment.getBook().getContent(), font);
		document.add(chunk);
		document.close();
		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	public List<Payment> refundAmount(String emailId, Integer bookId) {
		Payment paymentRes = paymentRepository.findByEmailAndBookId(emailId, bookId);
		paymentRes.setPaymentStatus(PaymentStatus.CANCLLED);
		paymentRepository.save(paymentRes);
		return getPurchasedBooks(emailId);
	}

	public List<Payment> serachBooksByPaymentId(String emailId, Integer paymentId) {
		return paymentRepository.findByIdAndEmail(paymentId, emailId);

	}

}
