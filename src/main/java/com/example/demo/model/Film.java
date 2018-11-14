package com.example.demo.model;

import com.example.demo.model.generic.DataModelObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.chart.PieChart;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//@XmlRootElement
@Entity
@Table(name = "film")
@SequenceGenerator(name = "idgen", sequenceName = "SEQ_FILM_ID")
@Getter
@Setter
public class Film extends DataModelObject{

    @Size(max = 60)
    @Column(name = "title")
    private String title;
    @Column(name = "release_year")
    private Integer year;
    @Column(name = "description")
    private String description;
    @JsonIgnore//todo delete this ?
    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "film", fetch = FetchType.LAZY, cascade = CascadeType
            .ALL)
    private Set<FilmRelations> filmRelations = new HashSet<>();
//    private LocalDate creationDate;
//    private LocalDate modificationDate;
//    private Set<FilmComments> filmComments = new LinkedHashSet<>();

    public Film() {

    }

    public Film(String title, Integer year, String description) {
        this.title = title;
        this.year = year;
        this.description = description;
    }


    public Set<FilmRelations> getFilmRelations() {
        return filmRelations;
    }

    public void setFilmRelations(Set<FilmRelations> filmRelations) {
        this.filmRelations = filmRelations;
    }

    public void addRelation(FilmRelations relation) {
        if(filmRelations == null || filmRelations.isEmpty()) {
            filmRelations = new HashSet<>();
        }
        filmRelations.add(relation);
    }

    public Film(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Film(String title, int year, String description) {
        this.title = title;
        this.year = year;
        this.description = description;
    }

    //    @Column(name = "CREATION_DATE")
//    public Date getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(Date creationDate) {
//        this.creationDate = creationDate;
//    }
//
//    @Column(name = "MODIFICATION_DATE")
//    public Date getModificationDate() {
//        return modificationDate;
//    }

//    public void setModificationDate(Date modificationDate) {
//        this.modificationDate = modificationDate;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "film_FILM_ID")
//    public Set<FilmComments> getFilmComments() {
//        return filmComments;
//    }
//
//    public void setFilmComments(Set<FilmComments> filmComments) {
//        this.filmComments = filmComments;
//    }
//
//    public void addComment(FilmComments filmComment) {
//        if(filmComments == null) {
//            filmComments = new LinkedHashSet<>();
//        }
//        filmComments.add(filmComment);
//    }

}
