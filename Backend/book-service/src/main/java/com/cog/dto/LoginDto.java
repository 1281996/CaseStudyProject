package com.cog.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class LoginDto  implements Serializable{
	
	private static final long serialVersionUID = 658264558864507980L;
	@NotBlank(message = "Email Id cannot be Empty")
	private String emailId;
	@NotBlank(message = "Password cannot be Empty")

	private String password;
	public LoginDto() {
		
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginDto [emailId=" + emailId + ", password=" + password + "]";
	}
	
}