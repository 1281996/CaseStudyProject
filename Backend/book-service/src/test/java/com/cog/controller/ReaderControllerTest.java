package com.cog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import com.cog.entity.Book;
import com.cog.entity.Payment;
import com.cog.service.BookService;
import com.itextpdf.text.DocumentException;

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

	@Test
	void testGetPurchasedBooks() {
		List<Payment> payments = new ArrayList<>();
		payments.add(getPayment());
		when(bookService.getPurchasedBooks("kamma.mallika@gmail.com")).thenReturn(payments);
		assertEquals(1, readerController.getPurchasedBooks("kamma.mallika@gmail.com").size());
	}

	@Test
	void testExportBookContentToPDF() throws DocumentException, IOException {

		when(bookService.getBookContent("kamma.mallika@gmail.com", 1))
				.thenReturn(new ByteArrayInputStream(new byte[10]));
		MockHttpServletResponse response = new MockHttpServletResponse();
		readerController.exportBookContentToPDF(response, "kamma.mallika@gmail.com", 1);
	}

	@Test
	void testRefundAmount() {
		List<Payment> payments = new ArrayList<>();
		payments.add(getPayment());
		when(bookService.refundAmount("kamma.mallika@gmail.com", 1)).thenReturn(payments);
		assertEquals(1, readerController.refundAmount("kamma.mallika@gmail.com", 1).size());
	}

	@Test
	void testSerachBooksByPaymentId() {
		List<Payment> payments = new ArrayList<>();
		payments.add(getPayment());
		when(bookService.serachBooksByPaymentId("kamma.mallika@gmail.com", 1)).thenReturn(payments);
		assertEquals(1, readerController.serachBooksByPaymentId(getPayment(), "kamma.mallika@gmail.com", 1).size());
	}

	Payment getPayment() {
		Payment payment = new Payment();
		payment.setBook(new Book());
		payment.setCardNumber(12345L);
		payment.setCvc(234L);
		payment.setEmail("kamma.mallika@gmail.com");
		payment.setId(1);
		payment.setName("mallika");
		payment.setPaymentDate(LocalDateTime.now());
		payment.setPaymentType("Card");
		payment.setRefundStatus(true);
		return payment;
	}
}
