package com.cog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cog.dto.BookDto;
import com.cog.dto.BuyDto;
import com.cog.dto.ResponseDto;
import com.cog.entity.Book;
import com.cog.entity.Payment;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.enums.Category;
import com.cog.enums.Event;
import com.cog.repository.BookRepository;
import com.cog.repository.PaymentRepository;
import com.cog.util.Constant;
import com.itextpdf.text.DocumentException;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	@Mock
	BookRepository bookRepository;
	@Mock
	RoleService roleService;
	@Mock
	UserService userService;
	@InjectMocks
	BookService bookService;
	@Mock
	PaymentRepository paymentRepository;
	@Mock
	RestTemplate restTemplate;

	@Test
	void testSaveBookWhenSucess() {
		Book book = getBook();
		lenient().when(bookRepository.save(book)).thenReturn(book);
		when(bookRepository.findAll()).thenReturn(getBookDtoList());
		when(roleService.findById(Constant.ROLE_AUTHOR_ID)).thenReturn(new Role());
		when(userService.findByUserId(1)).thenReturn(new User());
		assertEquals(1, bookService.saveBook(getBookDto(), 1).getBookDto().size());
	}

	@Test
	void testSaveBookWhenFailure() {
		Book book = getBook();
		Mockito.lenient().when(bookRepository.save(book)).thenReturn(null);
		assertEquals(Constant.BOOK_MSG_FALIURE, bookService.saveBook(getBookDto(), 1).getResponse());
	}

	@Test
	void testEditBook() {
		Optional<Book> book = Optional.of(getBook());
		when(bookRepository.findById(1)).thenReturn(book);
		when(bookRepository.findAll()).thenReturn(getBookDtoList());
		assertEquals(1, bookService.editBook(getBookDto(), 1, 1).getBookDto().size());
	}

	@Test
	void testEditBookWhenFailure() {
		Optional<Book> book = Optional.ofNullable(null);
		when(bookRepository.findById(1)).thenReturn(book);
		String actual = assertThrows(NullPointerException.class, () -> bookService.editBook(getBookDto(), 1, 1))
				.getMessage();
		assertEquals("Book Not Found with BookId: 1", actual);
	}

	@Test
	void testGetAllMyBooks() {
		when(bookRepository.findAll()).thenReturn(getBookDtoList());
		assertEquals(1, bookService.getAllMyBooks().size());
	}

	@Test
	void testGetReaderBooks() {
		when(bookRepository.findByStatus(Event.UNBLOCK)).thenReturn(getBookDtoList());
		assertEquals(1, bookService.getReaderBooks().size());
	}

	@Test
	void testGetFilteredBooks() {
		when(userService.findByUserId(1)).thenReturn(getUser());
		when(bookRepository.findByCategoryUserIdPublisherPrice("HORROR", 1, "Rupa", BigDecimal.valueOf(250)))
				.thenReturn(getBookDtoList());
		assertEquals(1, bookService.getFilteredBooks(Category.HORROR, 1, BigDecimal.valueOf(250), "Rupa").size());
	}

	@Test
	void testGetDistinctPublisherList() {
		List<String> publishers = new ArrayList<>();
		publishers.add("Rupa");
		when(bookRepository.findDistinctPublishers()).thenReturn(publishers);
		assertEquals(1, bookService.getDistinctPublisherList().size());
	}

	@Test
	void testBuyBook() {
		BuyDto buyDto = getBuyDto();
		String uri = "http://localhost:8081/card/" + buyDto.getCardNumber();
		when(restTemplate.getForObject(uri, BuyDto.class)).thenReturn(buyDto);
		when(paymentRepository.findByEmailAndBookId(buyDto.getEmail(), 1)).thenReturn(null);
		Optional<Book> optionalBook = Optional.of(getBook());
		when(bookRepository.findById(1)).thenReturn(optionalBook);
		String uriUpdate = "https://4d2vz23n9h.execute-api.us-east-1.amazonaws.com/prod/"+ buyDto.getCardNumber() + "/" + buyDto.getAmount();
		ResponseDto dto = new ResponseDto();
		dto.setResponse("");
		ResponseEntity<ResponseDto> resEntity = new ResponseEntity<>(dto, HttpStatus.OK);
		HttpEntity<BuyDto> entity = new HttpEntity<BuyDto>(buyDto);
		when(restTemplate.exchange(uriUpdate, HttpMethod.PUT, entity, ResponseDto.class)).thenReturn(resEntity);
		assertEquals("", bookService.buyBook(buyDto).getResponse());
	}

	@Test
	void testBuyBookWhenInsuffientBalance() {
		BuyDto input = getBuyDto();
		BuyDto output = getBuyDto();
		output.setAmount(5.0);
		String uri = "http://localhost:8081/card/" + input.getCardNumber();
		when(restTemplate.getForObject(uri, BuyDto.class)).thenReturn(output);
		assertEquals("Insufficient Balance", bookService.buyBook(input).getResponse());
	}

	@Test
	void testBuyBookWhenNullCardDetails() {
		BuyDto input = getBuyDto();

		String uri = "http://localhost:8081/card/" + input.getCardNumber();
		when(restTemplate.getForObject(uri, BuyDto.class)).thenReturn(null);
		assertEquals("Incorrect Card Number", bookService.buyBook(input).getResponse());
	}

	@Test
	void testGetPurchasedBooks() {
		List<Payment> list = new ArrayList<>();
		list.add(getPayment());
		when(paymentRepository.findByEmail("kamma@gmail.com")).thenReturn(list);
		assertEquals(1, bookService.getPurchasedBooks("kamma@gmail.com").size());
	}

	@Test
	void testGetBookContent() throws FileNotFoundException, DocumentException {
		when(paymentRepository.findByEmailAndBookId("kamma@gmail.com", 1)).thenReturn(getPayment());
		assertEquals(891, bookService.getBookContent("kamma@gmail.com", 1).available());
	}

	@Test
	void testRefundAmount() {
		List<Payment> list = new ArrayList<>();
		Payment paymentRes = getPayment();
		list.add(paymentRes);
		when(paymentRepository.findByEmailAndBookId("kamma@gmail.com", 1)).thenReturn(paymentRes);
		assertEquals(0, bookService.refundAmount("kamma@gmail.com", 1).size());
	}

	@Test
	void serachBooksByPaymentId() {
		List<Payment> list = new ArrayList<>();
		Payment paymentRes = getPayment();
		list.add(paymentRes);
		when(paymentRepository.findByIdAndEmail(1, "kamma@gmail.com")).thenReturn(list);
		assertEquals(1, bookService.serachBooksByPaymentId("kamma@gmail.com", 1).size());
	}

	public static List<Book> getBookDtoList() {
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		book.setCategory(Category.FANTASY);
		book.setContent("...");
		book.setId(1);
		book.setImage("assests/img.jpg");
		book.setPrice(BigDecimal.valueOf(250));
		book.setPublisher("Rupa");
		book.setPublishedDate(LocalDate.now());
		book.setRole(new Role());
		book.setStatus(Event.ACTIVE);
		book.setTitle("Harry Poter");
		book.setUser(new User());
		books.add(book);
		return books;
	}

	@Test
	void testGetAllMyBooksByAuthorId() {
		when(bookRepository.findByUserId(1)).thenReturn(getBookDtoList());
		assertEquals(1, bookService.getAllMyBooksByAuthorId(1).size());
	}

	public static Book getBook() {

		Book book = new Book();
		book.setCategory(Category.FANTASY);
		book.setContent("...");
		book.setId(1);
		book.setImage("assests/img.jpg");
		book.setPrice(BigDecimal.valueOf(250));
		book.setPublisher("Rupa");
		book.setPublishedDate(LocalDate.now());
		book.setRole(new Role());
		book.setStatus(Event.ACTIVE);
		book.setTitle("Harry Poter");
		book.setUser(new User());

		return book;
	}

	public static BookDto getBookDto() {

		BookDto book = new BookDto();
		book.setCategory(Category.FANTASY);
		book.setContent("...");
		book.setId(1);
		book.setImage("assests/img.jpg");
		book.setPrice(BigDecimal.valueOf(250));
		book.setPublisher("Rupa");
		book.setPublishedDate(LocalDate.now());
		book.setRole(new Role());
		book.setStatus(Event.ACTIVE);
		book.setTitle("Harry Poter");
		book.setUser(new User());

		return book;
	}

	public static User getUser() {
		User user = new User();
		user.setEmailId("kamma.mallika@gmail.com");
		user.setFirstName("kamma");
		user.setLastName("mallika");
		user.setPassword("mkllll");
		user.setId(1);
		user.setRegisteredDate(LocalDate.now());
		return user;
	}

	public static BuyDto getBuyDto() {
		BuyDto buyDto = new BuyDto();
		buyDto.setBookId(1);
		buyDto.setCardNumber(8999L);
		buyDto.setCvc(6778L);
		buyDto.setEmail("kamma@gmail.com");
		buyDto.setName("kamma");
		buyDto.setAmount(567.0);
		List<BookDto> bookDtos = new ArrayList<>();
		bookDtos.add(getBookDto());
		buyDto.setBooks(bookDtos);
		return buyDto;
	}

	public static Payment getPayment() {
		Payment payment = new Payment();
		payment.setCardNumber(8999L);
		payment.setCvc(6778L);
		payment.setEmail("kamma@gmail.com");
		payment.setName("kamma");
		Book book = getBook();
		payment.setBook(book);
		payment.setPaymentDate(LocalDateTime.now());
		return payment;
	}
}
