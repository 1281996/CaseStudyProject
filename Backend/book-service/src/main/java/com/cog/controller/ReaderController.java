package com.cog.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.time.LocalDateTime;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.io.IOUtils;
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

import com.cog.entity.Payment;
import com.cog.service.BookService;
import com.itextpdf.text.DocumentException;

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

	@GetMapping("/{emailId}/books")
	List<Payment> getPurchasedBooks(@PathVariable String emailId) {
		LOGGER.info("getPurchasedBooks");
		return bookService.getPurchasedBooks(emailId);
	}

	@GetMapping("/{emailId}/books/{bookId}")
	public void exportBookContentToPDF(HttpServletResponse response, @PathVariable("emailId") String emailId,
			@PathVariable Integer bookId) throws IOException, DocumentException {
		LOGGER.info("exportBookContentToPDF");
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=content" + LocalDateTime.now() + ".pdf";
		response.setHeader(headerKey, headerValue);
		ByteArrayInputStream contentStream = bookService.getBookContent(emailId, bookId);
		IOUtils.copy(contentStream, response.getOutputStream());
	}

	@GetMapping("/{emailId}/books/{bookId}/refund")
	public List<Payment> refundAmount(@PathVariable("emailId") String emailId,
			@PathVariable Integer bookId) {
		LOGGER.info("refundAmount");
		return bookService.refundAmount(emailId, bookId);
	}

	@PostMapping("/{emailId}/books/{paymentId}")
	public List<Payment> serachBooksByPaymentId(@RequestBody Payment payment,@PathVariable("emailId") String email
			,@PathVariable("paymentId") Integer paymentId) {
		LOGGER.info("serachBooksByPaymentId");
		return bookService.serachBooksByPaymentId(email,paymentId);
	}
}
