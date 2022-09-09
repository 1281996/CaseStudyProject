package com.cog.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_tran")
@Getter
@Setter
public class Payment {
	@Id
	@SequenceGenerator(name = "gen1", sequenceName = "payment_sequence")
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	private Integer id;

	@ManyToOne(targetEntity = Book.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id", referencedColumnName = "id")
	private Book book;

	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "card_no")
	private Long cardNumber;
	@Column(name = "cvc")
	private Long cvc;

	private String email;
	private String name;

}
