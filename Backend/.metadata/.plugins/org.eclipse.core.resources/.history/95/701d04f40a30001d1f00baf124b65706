package com.cog.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private Category category;
	@NotBlank(message = "Image can't be empty")
	private String image;
	private BigDecimal price;
	@NotBlank(message = "content can't be empty")
	private String content;
	@Enumerated(EnumType.STRING)
	private Event status;

	private Integer id;

	private User user;

	private Role role;

	private LocalDate publishedDate;

}
