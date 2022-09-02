package com.cog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cog.dto.BookDto;
import com.cog.dto.ResponseDto;
import com.cog.service.BookService;

@RequestMapping("/api/v1/digitalbooks/readers")
@RestController
@CrossOrigin
public class ReaderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReaderController.class);

}
