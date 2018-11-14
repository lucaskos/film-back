package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

	private int id;
	private String role;
	private User user;

	public Role() {

	}

	public Role(String role) {
		this.role = role;
	}

	public Role(String email, String userRole) {
	}

    @Id
	@GeneratedValue
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinTable(name="users_roles",
			joinColumns = {@JoinColumn(name="users_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name="roles_id", referencedColumnName="id")}
	)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
