package com.luke.filmdb.application.model.user;

import lombok.Getter;
import lombok.Setter;

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
import java.util.Objects;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

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
        //todo fix problem in mapper must have public constructor
    }

    public static Role getRoleInstance() {
        return new Role();
    }

    public static Role getRoleWithNameAndId(Long id, String roleName) {
        Role roleInstance = getRoleInstance();
        roleInstance.setId(id);
        roleInstance.setRoleName(roleName);
        return roleInstance;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return roleName != null ? roleName.hashCode() : 0;
    }
}
