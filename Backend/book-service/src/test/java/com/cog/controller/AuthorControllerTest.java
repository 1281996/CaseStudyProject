package com.cog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.dto.BookDto;
import com.cog.dto.BookResDto;
import com.cog.dto.LoginDto;
import com.cog.dto.ResponseDto;
import com.cog.dto.UserDto;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.service.BookService;
import com.cog.service.UserDetailsServiceImpl;
import com.cog.util.Constant;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
	@Mock
	UserDetailsServiceImpl userService;

	@Mock
	BookService bookService;
	@InjectMocks
	AuthorController authorController;

	@Test
	void testCreateUser() {
		UserDto dto = getUserDto();
		when(userService.saveUser(dto)).thenReturn(new ResponseDto(Constant.USER_REGISTER_SUCCESS));
		assertEquals(Constant.USER_REGISTER_SUCCESS, authorController.createUser(dto).getResponse());
	}

	@Test
	void testLoginUser() {
		LoginDto dto = getLoginDto();
		when(userService.vaidateUser(dto)).thenReturn(new ResponseDto(Constant.USER_CHECK_CREDENTAILS_SUCESS));
		assertEquals(Constant.USER_CHECK_CREDENTAILS_SUCESS, authorController.loginUser(dto).getResponse());
	}

	@Test
	void testCreateBook() {
		BookDto bookDto = getBookDto();
		when(bookService.saveBook(bookDto, 1)).thenReturn(getBookResDto());
		assertEquals(Constant.BOOK_MSG_SUCCESS, authorController.createBook(bookDto, 1).getResponse());
	}

	@Test
	void testUpdateBook() {
		BookDto bookDto = getBookDto();
		when(bookService.editBook(bookDto, 1, 1)).thenReturn(getBookResDto());
		assertEquals(Constant.BOOK_MSG_SUCCESS, authorController.updateBook(bookDto, 1, 1).getResponse());

	}

	@Test
	void testGetAllMyBooks() {
		when(bookService.getAllMyBooks()).thenReturn(new ArrayList<>());
		assertEquals(0, authorController.getAllMyBooks(1).size());

	}

	public static BookDto getBookDto() {

		BookDto book = new BookDto();
		book.setCategory("Fictional");
		book.setContent("...");
		book.setId(1);
		book.setImage("assests/img.jpg");
		book.setPrice(250.0);
		book.setPublisher("Rupa");
		book.setReleasedDate(LocalDateTime.now());
		book.setRole(new Role());
		book.setStatus("Active");
		book.setTitle("Harry Poter");
		book.setUser(new User());

		return book;
	}

	public static UserDto getUserDto() {
		UserDto userDto = new UserDto();
		userDto.setEmailId("kamma.mallika@gmail.com");
		userDto.setFirstName("kamma");
		userDto.setLastName("mallika");
		userDto.setPassword("mkllll");
		userDto.setRole(Constant.ROLE_AUTHOR);
		return userDto;
	}

	public static LoginDto getLoginDto() {
		LoginDto dto = new LoginDto();
		dto.setEmailId("kamma.mallika@gmail.com");
		dto.setPassword("pa");
		return dto;
	}

	public static BookResDto getBookResDto() {
		BookResDto dto = new BookResDto();
		dto.setResponse(Constant.BOOK_MSG_SUCCESS);
		return dto;
	}
}
