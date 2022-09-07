package com.cog.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginDto implements Serializable {

	private static final long serialVersionUID = 658264558864507980L;
	@NotBlank(message = "Email Id cannot be Empty")
	private String emailId;
	@NotBlank(message = "Password cannot be Empty")

	private String password;

}
