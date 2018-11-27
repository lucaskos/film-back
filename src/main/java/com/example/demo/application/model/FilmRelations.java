package com.example.demo.application.model;

import com.example.demo.application.model.cache.dictionaries.PersonRole;
import lombok.Data;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Luke on 29.10.2017.
 */
@Entity
@Table(name = "FILM_RELATION")
@Getter
@Setter
public class FilmRelations {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ROLE")
    private String role;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FILM_ID")
    private Film film;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Person person;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ROLE_ID")
    private PersonRole personRoleDictionary;
}
