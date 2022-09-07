package com.cog.dto;

import java.io.Serializable;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResDto implements Serializable {

	private static final long serialVersionUID = -6631725384732301237L;

	private List<BookDto> bookDto;
	private String response;

}