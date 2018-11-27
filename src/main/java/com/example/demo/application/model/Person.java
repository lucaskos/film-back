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
@Table(name = "PERSON")
@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 5650070241555490348L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "BORN_DATE")
    private Date bornDate;
    @Column(name = "DIED_DATE")
    private Date diedDate;
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_PERSON_ID")
    private Set<PersonComments> personComments;
    @Column(name = "BIOGRAPHY", columnDefinition = "TEXT")
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
}
