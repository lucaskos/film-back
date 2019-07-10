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
@Table(name = "FILM_COMMENTS")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FilmComment extends Comment {

	@ManyToOne
	@JoinColumn(name = "film_id", nullable = true)
	private Film filmId;
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = true)
	private User userId;
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_comment_id")
	private FilmComment parentCommentId;
	@JsonIgnore
	@OneToMany(mappedBy = "parentCommentId")
	private Set<FilmComment> subComments = new HashSet<>();


	@Override
	public String toString() {
		return "FilmComment{" +
				"filmId=" + filmId +
				", userId=" + userId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		FilmComment that = (FilmComment) o;

		return Objects.equals(filmId, that.filmId) &&
				Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), filmId, userId);
	}
}
