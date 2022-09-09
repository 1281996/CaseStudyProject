package com.cog.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cog.enums.Category;
import com.cog.enums.Event;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book_tran")
@Getter
@Setter
public class Book implements Serializable {
	private static final long serialVersionUID = -3902187577963531742L;

	@Id
	@SequenceGenerator(name = "gen1", sequenceName = "book_sequence")
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	private Integer id;

	@ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne(targetEntity = Role.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

	private String publisher;

	private String title;
	@Enumerated(EnumType.STRING)
	private Category category;

	private String image;

	private BigDecimal price;

	private String content;

	@Column(name = "published_date")
	private LocalDate publishedDate;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Event status;

}
