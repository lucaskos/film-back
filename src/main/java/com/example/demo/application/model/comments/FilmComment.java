package com.example.demo.application.model.comments;

import com.example.demo.application.model.Film;
import com.example.demo.application.model.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FILM_COMMENTS")
@Data
public class FilmComment extends Comment {
	@ManyToOne
	@JoinColumn(name = "film_id", nullable = true)
	private Film filmId;
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = true)
	private User userId;

	public FilmComment() {
	}
}
