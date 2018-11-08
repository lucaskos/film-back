package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//@XmlRootElement
@Entity
@Table(name = "film")
@SequenceGenerator(name = "idgen", sequenceName = "SEQ_FILM_ID")
public class Film implements Serializable {

    private static final long serialVersionUID = -8915838577868975194L;
    private Long id;
    private String title;
    private Integer year;
    private String description;
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

    @JsonIgnore//todo delete this ?
    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "film", fetch = FetchType.LAZY, cascade = CascadeType
            .ALL)
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

    public Film(Long id, String title, int year, String description) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Size(max = 60)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "release_year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Film other = (Film) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Film [filmId=" + id + ", title=" + title + ", year=" + year + ", description=" + description
                + "]";
    }

}
