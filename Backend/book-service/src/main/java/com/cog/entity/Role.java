package com.cog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Table(name = "role_master")
@Entity
public class Role {
	@Id
	@SequenceGenerator(name = "gen1", sequenceName = "role_sequence")
	@GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "role_name")
	private String roleName;

	private String status;

	public Role() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	

}
