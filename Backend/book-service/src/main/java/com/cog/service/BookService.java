package com.cog.service;

import java.time.LocalDateTime;
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
	UserDetailsServiceImpl userServiceImpl;

	public BookResDto saveBook(BookDto bookDto, Integer authorId) {
		LOGGER.info("save book");
		Book book = new Book();
		setBook(bookDto, authorId, book);

		Book bookRes = bookRepository.save(book);

		if (bookRes != null) {
			return new BookResDto(getAllMyBooks(), Constant.BOOK_MSG_SUCCESS);

		}
		return new BookResDto(getAllMyBooks(), Constant.BOOK_MSG_FALIURE);
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
		book.setStatus(Constant.ACTIVE);
		book.setReleasedDate(LocalDateTime.now());
		return book;
	}

	public BookResDto editBook(BookDto bookDto, Integer authorId, Integer bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new NullPointerException("Book Not Found with BookId: " + bookId));
		setBook(bookDto, authorId, book);
		bookRepository.save(book);
		return new BookResDto(getAllMyBooks(), Constant.UPDATED_SUCCESS_MSG);
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
			dto.setReleasedDate(book.getReleasedDate());
			dto.setRole(book.getRole());
			dto.setStatus(book.getStatus());
			dto.setTitle(book.getTitle());
			dto.setUser(book.getUser());
			bookData.add(dto);
		});
		return bookData;
	}

}
