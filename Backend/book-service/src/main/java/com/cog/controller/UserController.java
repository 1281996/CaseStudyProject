package com.cog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cog.dto.ResponseDto;
import com.cog.dto.UserDto;
import com.cog.service.UserService;

@RestController
@RequestMapping("/api/v1/digitalbooks/author")
@CrossOrigin
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping(path = "/signup", consumes = "application/json", produces = "application/json")
	ResponseDto createUser(@Valid @RequestBody UserDto userDto) {
		return userService.saveUser(userDto);
	}
	
	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	ResponseDto loginUser(@Valid @RequestBody UserDto userDto) {
		return userService.checkCredentails(userDto);
	}

}