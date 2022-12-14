package com.cog.controller;

import java.math.BigDecimal;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.cog.dto.BookDto;
import com.cog.dto.BuyDto;
import com.cog.dto.ResponseDto;
import com.cog.enums.Category;
import com.cog.service.BookService;

@RequestMapping("/digitalbooks/books")
@RestController
@CrossOrigin
public class BookController extends BaseContoller {

	@Autowired
	BookService bookService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

	@GetMapping("/search/{category}/{author}/{price}/{publisher}")
	List<BookDto> getFilteredBooks(@PathVariable Category category, @PathVariable Integer author,
			@PathVariable BigDecimal price, @PathVariable String publisher) {
		LOGGER.info("getFilteredBooks");
		return bookService.getFilteredBooks(category, author, price, publisher);
	}

	@GetMapping("/publishers")
	List<String> getDistinctPublisherList() {
		LOGGER.info("getDistinctAuthorsRoleList");
		return bookService.getDistinctPublisherList();
	}

	@PostMapping("/buy")
	ResponseDto buyBook(@RequestBody BuyDto buyDto) {
		LOGGER.info("buyBook");
		return bookService.buyBook(buyDto);
	}
}
