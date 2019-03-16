package com.example.demo.application.model;

import com.example.demo.application.model.generic.DataModelObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//@XmlRootElement
@Entity
@Table(name = "FILM")
@Data
public class Film extends DataModelObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Size(max = 60)
    @Column(name = "TITLE")
    private String title;
    @Min(1800)
    @Column(name = "RELEASE_YEAR")
    private Integer year;
    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
    @JsonIgnore
    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "film", fetch = FetchType.EAGER, cascade = CascadeType
            .ALL)
    private Set<FilmRelations> filmRelations = new HashSet<>();
    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;
    @Column(name = "MODIFICATION_DATE")
    private LocalDate modificationDate;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        if (!super.equals(o)) return false;

        Film film = (Film) o;

        if (id != null ? !id.equals(film.id) : film.id != null) return false;
        if (title != null ? !title.equals(film.title) : film.title != null) return false;
        if (year != null ? !year.equals(film.year) : film.year != null) return false;
        if (description != null ? !description.equals(film.description) : film.description != null) return false;
        if (creationDate != null ? !creationDate.equals(film.creationDate) : film.creationDate != null) return false;
        return modificationDate != null ? modificationDate.equals(film.modificationDate) : film.modificationDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
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
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
