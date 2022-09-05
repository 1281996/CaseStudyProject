package com.cog.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cog.dto.BookDto;
import com.cog.dto.BookResDto;
import com.cog.dto.LoginDto;
import com.cog.dto.ResponseDto;
import com.cog.dto.UserDto;

import com.cog.service.BookService;

import com.cog.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/v1/digitalbooks/author")
@CrossOrigin
public class AuthorController {
	@Autowired
	UserDetailsServiceImpl userService;

	@Autowired
	BookService bookService;

	// @Autowired
	// JwtUtils jwtUtils;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

	@PostMapping(path = "/signup")
	ResponseDto createUser(@Valid @RequestBody UserDto signupDto) {
		LOGGER.info("createUser");
		return userService.saveUser(signupDto);
	}

	@PostMapping(path = "/login")
	ResponseDto loginUser(@Valid @RequestBody LoginDto loginDto) {
		LOGGER.info("loginUser");

		return userService.vaidateUser(loginDto);

	}

	@PostMapping(path = "/{authorId}/books")
	BookResDto createBook(@RequestBody BookDto bookDto, @PathVariable("authorId") Integer authorId) {
		LOGGER.info("createBook");
		return bookService.saveBook(bookDto, authorId);

	}

	@PutMapping(path = "/{authorId}/books/{bookId}")
	BookResDto updateBook(@RequestBody BookDto bookDto, @PathVariable("authorId") Integer authorId,
			@PathVariable("bookId") Integer bookId) {
		LOGGER.info("updateBook");
		return bookService.editBook(bookDto, authorId, bookId);

	}

	@GetMapping(path = "/{authorId}/books/display")
	List<BookDto> getAllMyBooks(@PathVariable("authorId") Integer authorId) {
		LOGGER.info("createBook");
		return bookService.getAllMyBooks();

	}

}
