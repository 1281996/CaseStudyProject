package com.cog.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cog.dto.BookDto;
import com.cog.dto.BookResDto;
import com.cog.dto.JwtResponse;
import com.cog.dto.LoginDto;
import com.cog.dto.ResponseDto;
import com.cog.dto.UserDto;
import com.cog.entity.UserMapping;
import com.cog.jwt.JwtUtils;
import com.cog.service.BookService;
import com.cog.service.UserDetailsImpl;
import com.cog.service.UserMappingService;
import com.cog.service.UserService;

@RestController
@RequestMapping("/digitalbooks/author")
@CrossOrigin
public class AuthorController extends BaseContoller {
	@Autowired
	UserService userService;

	@Autowired
	BookService bookService;

	@Autowired
	UserMappingService userMappingService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

	@PostMapping(path = "/signup")
	ResponseDto createUser(@Valid @RequestBody UserDto signupDto) {
		LOGGER.info("createUser");
		return userService.saveUser(signupDto);
	}

	@PostMapping(path = "/login")
	ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginDto loginDto) {
		LOGGER.info("loginUser");
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailId(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),

				userDetails.getEmail(), roles));

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
		return bookService.getAllMyBooksByAuthorId(authorId);

	}

	@GetMapping("/authorsRoles")
	List<UserMapping> getDistinctAuthorsList() {
		LOGGER.info("getDistinctAuthorsList");
		return userMappingService.findAuthorRoleUsers();
	}

}
