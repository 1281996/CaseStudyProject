package com.cog.service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cog.dto.BookDto;
import com.cog.dto.BookResDto;

import com.cog.entity.Book;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.enums.Event;
import com.cog.repository.BookRepository;
import com.cog.util.Constant;

@Service
public class BookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserMappingService.class);
	@Autowired
	BookRepository bookRepository;
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userServiceImpl;

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
		User user = userServiceImpl.findByUserId(authorId);
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
		List<BookDto> bookData = new ArrayList<>();
		bookRepository.findAll().forEach(book -> {
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
		List<BookDto> bookData = new ArrayList<>();
		 bookRepository.findByStatus(Event.UNBLOCK).forEach(book -> {
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

}
