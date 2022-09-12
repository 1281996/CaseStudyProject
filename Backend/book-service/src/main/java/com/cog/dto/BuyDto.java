package com.cog.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyDto implements Serializable {

	private static final long serialVersionUID = 4205654421606975861L;
	@NotBlank(message = "email can't be blank")
	private String email;
	@NotBlank(message = "name can't be blank")
	private String name;
	@NotBlank(message = "bookId can't be blank")
	private Integer bookId;
	@NotBlank(message = "cardNumber can't be blank")
	private Long cardNumber;
	@NotBlank(message = "cvc can't be blank")
	private Long cvc;
}
