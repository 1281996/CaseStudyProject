package com.cog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.dto.BookDto;
import com.cog.dto.BuyDto;
import com.cog.dto.ResponseDto;
import com.cog.enums.Category;
import com.cog.service.BookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	@Mock
	BookService bookService;
	@InjectMocks
	BookController bookController;

	@Test
	void testGetFilteredBooks() {
		List<BookDto> bookDtos = new ArrayList<>();
		bookDtos.add(new BookDto());
		when(bookService.getFilteredBooks(Category.HORROR, 1, BigDecimal.valueOf(0), "Rupa")).thenReturn(bookDtos);
		assertEquals(1, bookController.getFilteredBooks(Category.HORROR, 1, BigDecimal.valueOf(0), "Rupa").size());
	}

	@Test
	void testGetDistinctPublisherList() {
		List<String> list = new ArrayList<>();
		list.add("HORROR");
		when(bookService.getDistinctPublisherList()).thenReturn(list);
		assertEquals(1, bookController.getDistinctPublisherList().size());
	}

	@Test
	void testBuyBook() {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setResponse("Sucess");
		BuyDto buyDto = getBuyDto();
		when(bookService.buyBook(buyDto)).thenReturn(responseDto);
		assertEquals("Sucess", bookController.buyBook(buyDto).getResponse());
	}

	BuyDto getBuyDto() {
		BuyDto buyDto = new BuyDto();
		buyDto.setBookId(1);
		buyDto.setCardNumber(8999L);
		buyDto.setCvc(6778L);
		buyDto.setEmail("kamma@gmail.com");
		buyDto.setName("kamma");
		return buyDto;
	}
}
