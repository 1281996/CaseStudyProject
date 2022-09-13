package com.cog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "card_tran")
@Setter
@Getter
public class Card {
	@Id
	@SequenceGenerator(name = "gen", sequenceName = "card_sequence")
	@GeneratedValue(generator = "gen", strategy = GenerationType.SEQUENCE)
	private Integer id;

	private Long cardNumber;
	private Long cvc;
	private String email;
	private Double amount;

}
