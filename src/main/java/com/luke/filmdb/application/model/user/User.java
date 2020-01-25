package com.luke.filmdb.application.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luke.filmdb.application.model.ObjectRating;
import com.luke.filmdb.application.model.comments.PersonComment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;
//    @NotBlank
//    @Size(min = 5, max = 45)
    @Column(name = "username")
    public String username;
//    @NotBlank
    @Size(min = 5, max = 80)
    @Column(name = "password")
    public String password;
    @Column(name = "enabled")
    public boolean enabled;
//    @Email
    @Column(name = "email")
    public String email;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id")}
    )
//    @JoinColumn(name = "roles_id")
    public List<Role> roles;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "rating")
    public Set<ObjectRating> rating = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER")
    private Set<PersonComment> personComments;

    public User() {
    }

    public static User getInstance() {
        return new User();
    }

    public static User getUserWithUsernamePasswordRoles(String username, String password, List<Role> role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(role);
        return user;
    }

    public User(String username, String password, List<Role> role) {
        this.username = username;
        this.password = password;
        this.roles = role;
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
                .append(",email:" + email);
//        if(roles != null) {
//            sb.append(",roleName:"+roles.getRoleName()+"]");
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
            return other.username == null;
        } else return username.equals(other.username);
    }

}
