package com.cog.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cog.dto.BookDto;
import com.cog.dto.ResponseDto;
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

	public ResponseDto saveBook(BookDto bookDto, Integer authorId) {
		LOGGER.info("save book");
		Book book = setBook(bookDto, authorId);
		Book bookRes = bookRepository.save(book);
		ResponseDto dto = new ResponseDto();
		dto.setResponse("Book cereation failed");
		if (bookRes != null) {
			dto.setResponse("Book cereated Successfully");
		}
		return dto;
	}

	private Book setBook(BookDto bookDto, Integer authorId) {
		Book book = new Book();
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

	public ResponseDto editBook(BookDto bookDto, Integer authorId, Integer bookId) {
		Book book = bookRepository.findById(bookId).get();
		return null;
	}

}