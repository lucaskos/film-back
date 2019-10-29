package com.example.demo.application.model.comments;

import com.example.demo.application.model.Film;
import com.example.demo.application.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "FILM_COMMENT")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FilmComment extends Comment {

    @ManyToOne
    @JoinColumn(name = "film", nullable = true)
    private Film film;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_comment")
    private FilmComment parentComment;
    @JsonIgnore
    @OneToMany(mappedBy = "parentComment")
    private Set<FilmComment> subComments = new HashSet<>();


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
