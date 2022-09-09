package com.cog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.dto.BookDto;
import com.cog.dto.BuyDto;
import com.cog.entity.Book;
import com.cog.entity.Payment;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.enums.Category;
import com.cog.enums.Event;
import com.cog.repository.BookRepository;
import com.cog.repository.PaymentRepository;
import com.cog.util.Constant;

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
		BuyDto buyDto=getBuyDto();
		Optional<Book> book=Optional.of(getBook());
		when(bookRepository.findById(buyDto.getBookId())).thenReturn(book);
		when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(getPayment());
		assertEquals("Payment Failure", bookService.buyBook(buyDto).getResponse());
	
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
		return buyDto;
	}

	public static Payment getPayment() {
		Payment payment = new Payment();
		payment.setCardNumber(8999L);
		payment.setCvc(6778L);
		payment.setEmail("kamma@gmail.com");
		payment.setName("kamma");
		Book book=getBook();
		payment.setBook(book);
		return payment;
	}
}
