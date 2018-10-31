package com.example.demo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
@SequenceGenerator(name = "idgen", sequenceName = "SEQ_PERSON_ID")
public class Person implements Serializable {

    private static final long serialVersionUID = 5650070241555490348L;
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime bornDate;
    private LocalDateTime diedDate;
    private String bio;
    private Set<Role> role;

//    @JsonIgnore
//    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType
//            .ALL)
//    private List<FilmRelations> filmRelations = new ArrayList<>(0);
//
//    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType
//            .ALL)
//    public List<FilmRelations> getFilmRelations() {
//        return filmRelations;
//    }
//
//    public void setFilmRelations(List<FilmRelations> filmRelations) {
//        this.filmRelations = filmRelations;
//    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Person() {
    }

    public Person(Long id, String firstName, String lastName, LocalDateTime bornDate, LocalDateTime diedDate, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornDate = bornDate;
        this.diedDate = diedDate;
        this.bio = bio;
    }

    public Person(String firstName) {
        this.firstName = firstName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "born_date")
    public LocalDateTime getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDateTime bornDate) {
        this.bornDate = bornDate;
    }

    @Column(name = "died_date")
    public LocalDateTime getDiedDate() {
        return diedDate;
    }

    public void setDiedDate(LocalDateTime diedDate) {
        this.diedDate = diedDate;
    }

    @Column(name = "biography", columnDefinition = "TEXT")
    @Lob
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "person_PERSON_ID")
//    public Set<PersonComments> getPersonComments() {
//        return personComments;
//    }
//
//    public void setPersonComments(Set<PersonComments> personComments) {
//        this.personComments = personComments;
//    }
//
//    public void addPersonComments(PersonComments comment) {
//        if (this.personComments == null) {
//            personComments = new LinkedHashSet<>();
//        }
//        this.personComments.add(comment);
//
//    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
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
        Person other = (Person) obj;
        if (id != other.id)
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        return true;
    }


}
