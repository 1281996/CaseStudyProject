package com.cog.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.enums.Category;
import com.cog.enums.Event;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto implements Serializable {

	private static final long serialVersionUID = 4405087152380643868L;
	@NotBlank(message = "Publisher can't be empty")
	private String publisher;
	@NotBlank(message = "Title can't be empty")
	private String title;
	@NotBlank(message = "Category can't be empty")
	private Category category;
	@NotBlank(message = "Image can't be empty")
	private String image;
	@NotBlank(message = "Price can't be empty")
	private Double price;
	@NotBlank(message = "content can't be empty")
	private String content;

	private Event status;

	private Integer id;

	private User user;

	private Role role;

	private LocalDate publishedDate;

}
