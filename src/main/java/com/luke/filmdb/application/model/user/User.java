package com.luke.filmdb.application.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

//    @JoinColumn(name = "OWNER")
//    private List<PersonComment> personComments;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (enabled != user.enabled) {
            return false;
        }
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(roles, user.roles)) return false;
        if (!Objects.equals(firstName, user.firstName)) return false;
        return Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
