package com.cog.dto;

import java.io.Serializable;

public class ResponseDto implements Serializable {

	private static final long serialVersionUID = -1503447402406643870L;
	String response;

	public ResponseDto(String response) {

		this.response = response;
	}

	public ResponseDto() {
		
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	

}
