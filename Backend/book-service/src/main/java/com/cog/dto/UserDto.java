package com.cog.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cog.util.Constant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends LoginDto implements Serializable {

	private static final long serialVersionUID = -3324069780098960854L;

	@NotBlank(message = "FirstName cannot be Empty")
	private String firstName;
	@NotBlank(message = "LastName cannot be Empty")
	private String lastName;
	private String role = Constant.ROLE_AUTHOR;

}
