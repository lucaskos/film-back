package com.example.demo.commons;

import com.example.demo.application.model.Film;
import com.example.demo.application.model.comments.FilmComment;
import com.example.demo.application.model.user.User;

import java.util.ArrayList;

public class CommentsCommon {
	public static String COMMENT_TEXT = "TEST_COMMENT";
	public static Long FILM_ID = 1L;
	public static Long USER_ID = 1L;

	public Film getFilm() {
		Film film = new Film();
		film.setFilmComments(new ArrayList<>());
		film.setId(1L);
		film.setTitle("TITLE");
		film.setYear(1000);
		return film;
	}

	public FilmComment getFilmComment() {
		FilmComment filmComment = new FilmComment();
		filmComment.setUserId(new User());//todo fix
		filmComment.setFilmId(getFilm());
		filmComment.setText(COMMENT_TEXT);
		return filmComment;
	}

}
