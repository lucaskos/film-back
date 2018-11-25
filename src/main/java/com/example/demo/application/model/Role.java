package com.example.demo.application.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
public class Role {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "role")
	private String role;
//	private User user;

	public Role(String email, String userRole) {

	}

//	@ManyToOne(cascade= CascadeType.MERGE)
//	@JoinTable(name="users_roles",
//			joinColumns = {@JoinColumn(name="users_id", referencedColumnName="id")},
//			inverseJoinColumns = {@JoinColumn(name="roles_id", referencedColumnName="id")}
//	)
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

}
