package com.cog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cog.dto.BookDto;


import com.cog.service.BookService;

@RequestMapping("/digitalbooks/readers")
@RestController
@CrossOrigin
public class ReaderController {

	@Autowired
	BookService bookService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ReaderController.class);

	@GetMapping
	List<BookDto> getReaderBooks() {
		LOGGER.info("getReaderBooks");
		return bookService.getReaderBooks();
	}
}
