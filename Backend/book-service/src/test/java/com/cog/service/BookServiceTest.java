package com.cog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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
import com.cog.entity.Book;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.enums.Category;
import com.cog.enums.Event;
import com.cog.repository.BookRepository;
import com.cog.util.Constant;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	@Mock
	BookRepository bookRepository;
	@Mock
	RoleService roleService;
	@Mock
	UserService userServiceImpl;
	@InjectMocks
	BookService bookService;

	@Test
	void testSaveBook() {
		Book book = getBook();
		
		lenient().when(bookRepository.save(book)).thenReturn(book);
		when(bookRepository.findAll()).thenReturn(getBookDtoList());
		when(roleService.findById(Constant.ROLE_AUTHOR_ID)).thenReturn(new Role());
		when(userServiceImpl.findByUserId(1)).thenReturn(new User());
		assertEquals(1, bookService.saveBook(getBookDto(), 1).getBookDto().size());
	}
	@Test
	void testSaveBook1() {
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
	void testEditBook1() {
		Optional<Book> book=Optional.ofNullable(null);
		when(bookRepository.findById(1)).thenReturn(book);
		String actual=assertThrows(NullPointerException.class, ()->
			bookService.editBook(getBookDto(), 1, 1)
		).getMessage();
		assertEquals("Book Not Found with BookId: 1", actual);
	}

	@Test
	void testGetAllMyBooks() {
		when(bookRepository.findAll()).thenReturn(getBookDtoList());
		assertEquals(1, bookService.getAllMyBooks().size());
	}

	public static List<Book> getBookDtoList() {
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		book.setCategory(Category.FANTASY);
		book.setContent("...");
		book.setId(1);
		book.setImage("assests/img.jpg");
		book.setPrice(250.0);
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
		book.setPrice(250.0);
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
		book.setPrice(250.0);
		book.setPublisher("Rupa");
		book.setPublishedDate(LocalDate.now());
		book.setRole(new Role());
		book.setStatus(Event.ACTIVE);
		book.setTitle("Harry Poter");
		book.setUser(new User());

		return book;
	}
}