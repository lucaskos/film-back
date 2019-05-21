package com.example.demo.application.model;

import com.example.demo.application.model.generic.DataModelObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Data
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
    @OneToMany(targetEntity = FilmComments.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "filmId", fetch = FetchType.LAZY)
    private List<FilmComments> filmComments = new ArrayList<>();

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
        this.id = id;
        this.title = title;
        this.year = year;
        this.description = description;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", filmRelations=" + filmRelations +
                '}';
    }
}
