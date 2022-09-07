package com.cog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.cog.service.UserService;

@RestController
@RequestMapping("/digitalbooks/author")
@CrossOrigin
public class AuthorController {
	@Autowired
	UserService userService;

	@Autowired
	BookService bookService;

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
	BookResDto createBook(@Valid @RequestBody BookDto bookDto, @PathVariable("authorId") Integer authorId) {
		LOGGER.info("createBook");
		return bookService.saveBook(bookDto, authorId);

	}

	@PutMapping(path = "/{authorId}/books/{bookId}")
	BookResDto updateBook(@Valid @RequestBody BookDto bookDto, @PathVariable("authorId") Integer authorId,
			@PathVariable("bookId") Integer bookId) {
		LOGGER.info("updateBook");
		return bookService.editBook(bookDto, authorId, bookId);

	}

	@GetMapping(path = "/{authorId}/books/display")
	List<BookDto> getAllMyBooks(@PathVariable("authorId") Integer authorId) {
		LOGGER.info("createBook");
		return bookService.getAllMyBooks();

	}

	// Error handling
	@ExceptionHandler(MethodArgumentNotValidException.class)
	Map<String, String> handleMethodArumentException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(err -> {
			String filedError = ((FieldError) err).getField();
			String msg = ((FieldError) err).getDefaultMessage();

			errors.put(filedError, msg);
		});
		return errors;
	}

}
