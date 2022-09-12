package com.cog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.service.BookService;

@ExtendWith(MockitoExtension.class)
class ReaderControllerTest {
	@Mock
	BookService bookService;
	@InjectMocks
	ReaderController readerController;

	@Test
	void testGetReaderBooks() {
		when(bookService.getReaderBooks()).thenReturn(new ArrayList<>());
		assertEquals(0, readerController.getReaderBooks().size());
	}

}
