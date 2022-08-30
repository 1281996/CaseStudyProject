package com.cog.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class UserDto implements Serializable {

	private static final long serialVersionUID = -3324069780098960854L;

	private Integer id;
	@NotBlank(message = "Email Id cannot be Empty")
	private String emailId;
	@NotBlank(message = "FirstName cannot be Empty")
	private String firstName;
	@NotBlank(message = "LastName cannot be Empty")
	private String lastName;
	@NotBlank(message = "Password cannot be Empty")

	private String password;

	private String role;

	public UserDto() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}