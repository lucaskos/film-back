package com.luke.filmdb.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luke.filmdb.application.model.comments.PersonComment;
import com.luke.filmdb.application.model.generic.DataModelObject;
import io.jsonwebtoken.lang.Collections;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends DataModelObject {

    private static final long serialVersionUID = 5650070241555490348L;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "born_date")
    private LocalDate bornDate;
    @Column(name = "died_date")
    private LocalDate diedDate;
    @Column(name = "biography", columnDefinition = "text")
    private String bio;
    @JsonBackReference
    @OneToMany(targetEntity = PersonComment.class, cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PersonComment> personComment = new ArrayList<>();

    //    private Set<PersonComments> personComments;
    //    @JoinColumn(name = "person_PERSON_ID")
    //    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnore
    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "person", cascade = {CascadeType
            .ALL})
    private List<FilmRelations> filmRelations = new ArrayList<>(0);

//    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType
//            .ALL)
//    public List<FilmRelations> getFilmRelations() {
//        return filmRelations;
//    }


    public Person() {
    }

    public static Person getPersonWithFirstAndLastName(String firstName,
                                                       String lastName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bornDate=" + bornDate +
                ", diedDate=" + diedDate +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", bio='" + bio + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        if (!super.equals(o)) return false;

        Person person = (Person) o;


        return Objects.equals(bio, person.bio)
                && Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName)
                && Objects.equals(bornDate, person.bornDate)
                && Objects.equals(diedDate, person.diedDate)
                && Objects.equals(personComment, person.personComment);
//                && Objects.equals(filmRelations, person.getFilmRelations());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (bornDate != null ? bornDate.hashCode() : 0);
        result = 31 * result + (diedDate != null ? diedDate.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (!Collections.isEmpty(personComment) ? personComment.hashCode() : 0);
        return result;
    }
}
