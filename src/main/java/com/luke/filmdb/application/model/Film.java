package com.luke.filmdb.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.model.generic.DataModelObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "film")
@Getter
@Setter
@JsonIgnoreProperties(value = {"creationDate", "modificationDate"},
        allowGetters = true)
public class Film extends DataModelObject {

    @Size(max = 60)
    @Column(name = "title")
    private String title;
    @Min(1800)
    @Column(name = "release_year")
    private Integer year;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @JsonIgnore
    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "film", cascade = {CascadeType
            .ALL}, fetch = FetchType.LAZY)
    private Set<FilmRelations> filmRelations = new HashSet<>();
    @JsonBackReference
    @OneToMany(targetEntity = FilmComment.class, cascade = CascadeType.ALL, mappedBy = "film", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FilmComment> filmComments = new ArrayList<>();

    public Film() {

    }

    public static Film getFilmWithTitleYearDescription(String title, Integer year, String description) {
        Film film = new Film();
        film.setTitle(title);
        film.setYear(year);
        film.setDescription(description);
        return film;
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

    public static Film getFilmWithTitleYear(String title, Integer year) {
        Film film = new Film();
        film.setTitle(title);
        film.setYear(year);
        return film;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                '}';
    }

}
