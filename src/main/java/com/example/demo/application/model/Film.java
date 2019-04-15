package com.example.demo.application.model;

import com.example.demo.application.model.generic.DataModelObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@XmlRootElement
@Entity
@Table(name = "film")
@Data
@JsonIgnoreProperties(value = {"creationDate", "modificationDate"},
        allowGetters = true)
public class Film extends DataModelObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Size(max = 60)
    @Column(name = "title")
    private String title;
    @Min(1800)
    @Column(name = "release_year")
    private Integer year;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "modification_date")
    private LocalDate modificationDate;
    @JsonIgnore
    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "film", cascade = {CascadeType
            .ALL})
    private Set<FilmRelations> filmRelations = new HashSet<>();
    @OneToMany(targetEntity = FilmComments.class, cascade = CascadeType.ALL)
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

    //todo move it to superclass
    @PrePersist
    protected void onCreate() {
        this.creationDate = this.modificationDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modificationDate = LocalDate.now();
    }
}
