package com.cog.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "payment_tran")

public class Payment {
	@Id
	@SequenceGenerator(name = "gen1", sequenceName = "payment_sequence")
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	private Integer id;

	@ManyToOne(targetEntity = Role.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", referencedColumnName = "id")
	private Book book;

	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@Column(name = "payment_type")
	private String paymentType;

}
