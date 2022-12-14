package com.cog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.cog.dto.BookDto;
import com.cog.dto.BookResDto;

import com.cog.dto.LoginDto;
import com.cog.dto.ResponseDto;
import com.cog.dto.UserDto;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.entity.UserMapping;
import com.cog.enums.Category;
import com.cog.enums.Event;
import com.cog.jwt.JwtUtils;
import com.cog.service.BookService;
import com.cog.service.UserDetailsImpl;
import com.cog.service.UserMappingService;
import com.cog.service.UserService;
import com.cog.util.Constant;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
	@Mock
	UserService userService;

	@Mock
	BookService bookService;
	@Mock
	UserMappingService userMappingService;
	@InjectMocks
	AuthorController authorController;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	JwtUtils jwtUtils;

	@Test
	void testCreateUser() {
		UserDto dto = getUserDto();
		ResponseDto responseDto = new ResponseDto();
		responseDto.setResponse(Constant.USER_REGISTER_SUCCESS);
		when(userService.saveUser(dto)).thenReturn(responseDto);
		assertEquals(Constant.USER_REGISTER_SUCCESS, authorController.createUser(dto).getResponse());
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
		when(bookService.getAllMyBooksByAuthorId(1)).thenReturn(new ArrayList<>());
		assertEquals(0, authorController.getAllMyBooks(1).size());

	}

	@Test
	void testGetDistinctAuthorsList() {
		List<UserMapping> userMappings = new ArrayList<>();
		userMappings.add(getUsergetUserMapping());
		when(userMappingService.findAuthorRoleUsers()).thenReturn(userMappings);
		assertEquals(1, authorController.getDistinctAuthorsList().size());
	}

	@Test
	void testHandleMethodArumentException() {
		BindingResult bindingResult = new BindException("msg", "msg");
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

		assertEquals(0, authorController.handleMethodArumentException(exception).size());
	}

	@Test
	void testLoginUser() {
		LoginDto loginDto = getLoginDto();
		GrantedAuthority authoritie = new SimpleGrantedAuthority("ROLE_AUTHOR");
		Set<GrantedAuthority> list = new HashSet<>();
		list.add(authoritie);
		UserDetailsImpl detailsImpl = new UserDetailsImpl(1L, "kammalli", loginDto.getEmailId(), loginDto.getPassword(),
				list);

		Authentication authentication = new UsernamePasswordAuthenticationToken(detailsImpl, list);
		when(authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailId(), loginDto.getPassword())))
				.thenReturn(authentication);
		when(jwtUtils.generateJwtToken(authentication)).thenReturn("yeyrbecbdc678");

		assertEquals(loginDto.getEmailId(), authorController.loginUser(loginDto).getBody().getEmail());
	}

	public static BookDto getBookDto() {

		BookDto book = new BookDto();
		book.setCategory(Category.FANTASY);
		book.setContent("...");
		book.setId(1);
		book.setImage("assests/img.jpg");
		book.setPrice(BigDecimal.valueOf(250));
		book.setPublisher("Rupa");
		book.setPublishedDate(LocalDate.now());
		book.setRole(new Role());
		book.setStatus(Event.ACTIVE);
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

	public static UserMapping getUsergetUserMapping() {
		UserMapping mapping = new UserMapping();
		mapping.setRole(new Role());
		mapping.setUser(new User());

		return mapping;
	}
}
