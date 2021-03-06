package com.example.demo.application.model;

import com.example.demo.application.model.comments.FilmComment;
import com.example.demo.application.model.comments.PersonComment;
import com.example.demo.application.model.generic.DataModelObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
@Data
public class Person extends DataModelObject implements Serializable {

    private static final long serialVersionUID = 5650070241555490348L;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "born_date")
    private Date bornDate;
    @Column(name = "died_date")
    private Date diedDate;
    @Column(name = "biography", columnDefinition = "text")
    private String bio;
    @JsonBackReference
    @OneToMany(targetEntity = PersonComment.class, cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PersonComment> personComment;

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
