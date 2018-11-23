package com.example.demo.application.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@Entity
@Table(name = "FILM")
@Data
public class Film implements Serializable {

    private static final long serialVersionUID = -8915838577868975194L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long filmId;
    @Size(max = 60)
    @Column(name = "TITLE")
    private String title;
    @Min(1800)
    @Column(name = "RELEASE_YEAR")
    private Integer year;
    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
//    private Set<FilmRelations> filmRelations = new HashSet<>();
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    public Film() {

    }

    public Film(String title, Integer year, String description) {
        this.title = title;
        this.year = year;
        this.description = description;
    }

//    @JsonIgnore
//    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "film", fetch = FetchType.LAZY, cascade = CascadeType
//            .ALL)
//    public Set<FilmRelations> getFilmRelations() {
//        return filmRelations;
//    }
//
//    public void setFilmRelations(Set<FilmRelations> filmRelations) {
//        this.filmRelations = filmRelations;
//    }


    public Film(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Film(Long id, String title, int year, String description) {
        this.filmId = id;
        this.title = title;
        this.year = year;
        this.description = description;
    }

}
