package com.cog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cog.dto.BookDto;
import com.cog.dto.ResponseDto;
import com.cog.service.BookService;

@RequestMapping("/api/v1/digitalbooks/author")
@RestController
public class BookController {

	@Autowired
	BookService bookService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

	@PostMapping(path = "{authorId}/books")
	ResponseDto createBook(@RequestBody BookDto bookDto, @PathVariable("authorId") Integer authorId) {
		LOGGER.info("createBook");
		return bookService.saveBook(bookDto, authorId);

	}

	@PutMapping(path = "{authorId}/books/{bookId}")
	ResponseDto updateBook(@RequestBody BookDto bookDto, @PathVariable("authorId") Integer authorId,
			@PathVariable("bookId") Integer bookId) {
		LOGGER.info("updateBook");
		return bookService.editBook(bookDto, authorId, bookId);

	}
}