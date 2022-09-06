package com.cog.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.cog.entity.Role;
import com.cog.entity.User;

public class BookDto implements Serializable {

	private static final long serialVersionUID = 4405087152380643868L;
	@NotBlank(message = "Publisher can't be empty")
	private String publisher;
	@NotBlank(message = "Title can't be empty")
	private String title;
	@NotBlank(message = "Category can't be empty")
	private String category;
	@NotBlank(message = "Image can't be empty")
	private String image;
	@NotBlank(message = "Price can't be empty")
	private Double price;
	@NotBlank(message = "content can't be empty")
	private String content;

	private String status;

	private Integer id;

	private User user;

	private Role role;

	private LocalDateTime releasedDate;

	public BookDto() {

	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setReleasedDate(LocalDateTime releasedDate) {
		this.releasedDate = releasedDate;
	}

}
