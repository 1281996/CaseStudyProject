package com.cog.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cog.enums.Event;

@Entity
@Table(name = "user_master")
public class User {
	@Id
	@SequenceGenerator(name = "gen1", sequenceName = "user_sequence")
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "registered_date")
	private LocalDateTime registeredDate;
	
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Event status = Event.ACTIVE;
	public User() {
		
	}

	

	public void setId(Integer id) {
		this.id = id;
	}

	

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	

	public void setRegisteredDate(LocalDateTime registeredDate) {
		this.registeredDate = registeredDate;
	}

	

	public void setStatus(Event status) {
		this.status = status;
	}



	public Integer getId() {
		return id;
	}



	

	

}
