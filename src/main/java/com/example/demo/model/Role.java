package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class Role {

	private Long id;
	private String role;
	private String email;

	public Role() {

	}

	public Role(String role) {
		this.role = role;
	}

	public Role(String role, String email) {
		this.role = role;
		this.email = email;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
