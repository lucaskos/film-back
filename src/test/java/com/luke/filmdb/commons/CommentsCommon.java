package com.luke.filmdb.commons;

import com.luke.filmdb.application.DTO.CommentsDTO;
import com.luke.filmdb.application.DTO.user.LoginUserDTO;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.model.comments.PersonComment;
import com.luke.filmdb.application.model.user.User;

import java.time.LocalDate;
import java.util.Collections;

public class CommentsCommon extends FilmMapperCommons {
    public static String COMMENT_TEXT = "TEST_COMMENT";
    public static Long FILM_ID = 1L;
    public static Long USER_ID = 1L;

    public FilmComment getFilmComment() {
        FilmComment filmComment = new FilmComment();
        filmComment.setOwner(new User());//todo fix
        filmComment.setText(COMMENT_TEXT);
        return filmComment;
    }

    public PersonComment getPersonComment() {
        PersonComment personComment = new PersonComment();
        personComment.setOwner(new User()); //todo fix
        personComment.setText(COMMENT_TEXT);
        return personComment;
    }

    public Person getPerson() {
        Person person = new Person();
        person.setFirstName("TEST");
        person.setLastName("TEST");
        person.getPersonComment().addAll(Collections.singleton(getPersonComment()));
        return person;
    }

    public CommentsDTO getCommentsDTO() {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setUser(new LoginUserDTO());
        commentsDTO.setText(COMMENT_TEXT);
        commentsDTO.setId(1L);
        commentsDTO.setTitle(COMMENT_TEXT);
        commentsDTO.setEntityId(1L);
        commentsDTO.setCreatedDate(LocalDate.now());
        return commentsDTO;
    }

    public Film getSimpleFilmWithSingleComment() {
        Film simpleFilm = getSimpleFilm();
        FilmComment filmComment = getFilmComment();
        simpleFilm.setFilmComments(Collections.singletonList(filmComment));
        return simpleFilm;

    }

}
