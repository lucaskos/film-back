package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    public String username;
    public Integer id;
    public String password;
    public boolean enabled;
    public String email;
    public List<Role> roles;
    //    public Set<Rating> rating = new HashSet<>();
    private Set<PersonComments> personComments;

    public User() {

    }

    public User(String username, String password, List<Role> role) {
        this.username = username;
        this.password = password;
        this.roles = role;
    }

    public User(String username, Integer id, String password, boolean enabled, String email, List<Role> roles) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.roles = roles;
    }

    public <T> User(String email, String username, String encode, boolean b, List<T> ts) {
        this.email = email;
        this.username = username;
        this.enabled = b;
        this.roles = (List<Role>) ts;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "id")}
    )
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank
    @Size(min = 5, max = 45)
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank
    @Size(min = 5, max = 80)
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Email
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER_ID")
    public Set<PersonComments> getPersonComments() {
        return personComments;
    }

    public void setPersonComments(Set<PersonComments> comments) {
        this.personComments = comments;
    }

    //    @JsonManagedReference
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId", cascade = CascadeType.ALL)
//    public Set<Rating> getRating() {
//        return rating;
//    }
//
//    public void setRating(Set<Rating> rating) {
//        this.rating = rating;
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User [username:" + username)
                .append(", password:" + password)
                .append(",enabled" + enabled)
                .append(",email:"+email);
//        if(roles != null) {
//            sb.append(",role:"+roles.getRole()+"]");
//        } else {
//            sb.append("]");
//        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (enabled != other.enabled)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
