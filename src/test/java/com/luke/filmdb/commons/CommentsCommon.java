package com.luke.filmdb.commons;

import com.luke.filmdb.application.DTO.CommentDTO;
import com.luke.filmdb.application.DTO.user.LoginUserDTO;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.commands.CommentCommand;
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

    public static FilmComment getFilmComment() {
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

    public static CommentDTO getCommentsDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setUser(new LoginUserDTO());
        commentDTO.setText(COMMENT_TEXT);
        commentDTO.setId(1L);
        commentDTO.setTitle(COMMENT_TEXT);
        commentDTO.setEntityId(1L);
        commentDTO.setCreatedDate(LocalDate.now());
        return commentDTO;
    }

    public static CommentDTO getFilmCommentType() {
        CommentDTO commentDTO = getCommentsDTO();
        commentDTO.setEntityType("FILM");
        return commentDTO;
    }

    public CommentDTO getPersonCommentType() {
        CommentDTO commentDTO = getCommentsDTO();
        commentDTO.setEntityType("PERSON");
        return commentDTO;
    }

    public Film getSimpleFilmWithSingleComment() {
        Film simpleFilm = getSimpleFilm();
        FilmComment filmComment = getFilmComment();
        simpleFilm.setFilmComments(Collections.singletonList(filmComment));
        return simpleFilm;
    }



    private CommentCommand getFilmCommand() {
        CommentCommand commentCommand = new CommentCommand();
        commentCommand.setEntityType("FILM");

        commentCommand.setCommentDTO(getFilmCommentDTO());

        return commentCommand;
    }

    private CommentCommand getPersonCommand() {
        CommentCommand commentCommand = new CommentCommand();
        commentCommand.setEntityType("PERSON");

        commentCommand.setCommentDTO(getPersonCommentDTO());

        return commentCommand;
    }

    protected CommentDTO getPersonCommentDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setEntityType("PERSON");
        commentDTO.setEntityId(PERSON_ID);
        return commentDTO;
    }

    protected CommentDTO getFilmCommentDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText(COMMENT_TEXT);
        commentDTO.setUser(new UserDTO());
        commentDTO.setEntityId(FILM_ID);
        commentDTO.setEntityType("FILM");
        return commentDTO;
    }


}
