package com.cog.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ResponseDto implements Serializable {

	private static final long serialVersionUID = -1503447402406643870L;
	private String response;
	private boolean flag;
	private Integer id;
}
