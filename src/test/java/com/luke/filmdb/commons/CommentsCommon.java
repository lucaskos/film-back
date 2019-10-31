package com.luke.filmdb.commons;

import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

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
		FilmComment filmComment = getFilmComment();
		film.setFilmComments(Arrays.asList(filmComment));
		return film;
	}

	public FilmComment getFilmComment() {
		FilmComment filmComment = new FilmComment();
		filmComment.setOwner(new User());//todo fix
		filmComment.setText(COMMENT_TEXT);
		return filmComment;
	}

}
