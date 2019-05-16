package com.example.demo.application.model.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "roles")
@Data
public class Role {

	public enum ROLE_TYPE {
		ADMIN, REVIEWER, USER, EDITOR
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "role")
	private String roleName;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "roles_privileges",
			joinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "privilege_id", referencedColumnName = "id"))
	private Collection<Privilege> privileges;
//	private User user;


	public Role() {
	}

	public Role(String role) {
		this.roleName = role;
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
