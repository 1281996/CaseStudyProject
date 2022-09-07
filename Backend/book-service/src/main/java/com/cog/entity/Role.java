package com.cog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "role_master")
@Entity
@Getter
@Setter
public class Role {
	@Id
	@SequenceGenerator(name = "gen1", sequenceName = "role_sequence")
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "role_name")
	private String roleName;

}
