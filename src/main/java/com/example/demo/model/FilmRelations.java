package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Luke on 29.10.2017.
 */
@Entity
@Table(name = "FILM_RELATION")
public class FilmRelations implements Serializable {

    private static final long serialVersionUID = -3368604415829986784L;
    private Integer filmRelationId;
    private String role;
    private Film film;
    private Person person;

    public FilmRelations() {

    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getFilmRelationId() {
        return filmRelationId;
    }

    public void setFilmRelationId(Integer filmRelationId) {
        this.filmRelationId = filmRelationId;
    }

    @Column(name = "ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

//    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

//    private PersonRole personRoleDictionary;
//
//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    public PersonRole getPersonRoleDictionary() {
//        return personRoleDictionary;
//    }
//
//    public void setPersonRoleDictionary(PersonRole personRoleDictionary) {
//        this.personRoleDictionary = personRoleDictionary;
//    }
}
