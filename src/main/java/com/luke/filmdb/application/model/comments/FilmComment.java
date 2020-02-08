package com.luke.filmdb.application.model.comments;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class FilmComment extends Comment {

    @ManyToOne
    @JoinColumn(name = "film", nullable = true)
    @JsonBackReference
    private Film film;

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
        int result = super.hashCode();
        result = 31 * result + (film != null ? film.hashCode() : 0);
        return result;
    }
}
