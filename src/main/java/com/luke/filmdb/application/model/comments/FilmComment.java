package com.luke.filmdb.application.model.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luke.filmdb.application.model.Film;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "FILM_COMMENT")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FilmComment extends Comment {

    @ManyToOne
    @JoinColumn(name = "film", nullable = true)
    private Film film;
//
//    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//    @JoinColumn(name = "parent_comment")
//    private FilmComment parentComment;
//    @JsonIgnore
//    @OneToMany(mappedBy = "parentComment")
//    private Set<FilmComment> subComments = new HashSet<>();


    public FilmComment() {
    }

    public FilmComment(Film film) {
        this.film = film;
    }

    @Override
    public String toString() {
        return "FilmComment{" +
                "film=" + film +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FilmComment that = (FilmComment) o;

        return Objects.equals(film, that.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), film);
    }
}
