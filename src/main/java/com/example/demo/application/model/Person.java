package com.example.demo.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 5650070241555490348L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "born_date")
    private Date bornDate;
    @Column(name = "died_date")
    private Date diedDate;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "modification_date")
    private Date modificationDate;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "person_PERSON_ID")
//    private Set<PersonComments> personComments;
    @Column(name = "biography", columnDefinition = "text")
    private String bio;
//    @JsonIgnore
//    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType
//            .ALL)
//    private List<FilmRelations> filmRelations = new ArrayList<>(0);

//    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType
//            .ALL)
//    public List<FilmRelations> getFilmRelations() {
//        return filmRelations;
//    }


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
}
