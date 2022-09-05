package com.cog.dto;

import java.io.Serializable;

import java.util.List;

public class BookResDto implements Serializable {

	private static final long serialVersionUID = -6631725384732301237L;

	private List<BookDto> bookDto;
	private String response;

	public BookResDto() {

	}

	public BookResDto(List<BookDto> bookDto, String response) {
		super();
		this.bookDto = bookDto;
		this.response = response;
	}

	public List<BookDto> getBookDto() {
		return bookDto;
	}

	public void setBookDto(List<BookDto> bookDto) {
		this.bookDto = bookDto;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BookResDto [bookDto=" + bookDto + ", response=" + response + "]";
	}

}
