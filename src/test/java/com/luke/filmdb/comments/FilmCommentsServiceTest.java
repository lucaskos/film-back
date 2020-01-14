package com.luke.filmdb.comments;

import com.luke.filmdb.application.DTO.CommentsDTO;
import com.luke.filmdb.application.DTO.mapper.CommentMapper;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.commands.CommentCommand;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.repository.FilmCommentsRepo;
import com.luke.filmdb.application.repository.FilmRepo;
import com.luke.filmdb.application.repository.PersonCommentsRepo;
import com.luke.filmdb.application.repository.PersonRepo;
import com.luke.filmdb.application.resource.filter.UserNotFoundException;
import com.luke.filmdb.application.services.CommentService;
import com.luke.filmdb.application.services.UserService;
import com.luke.filmdb.commons.CommentsCommon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmCommentsServiceTest extends CommentsCommon {

    @Mock
    private FilmRepo filmRepo;

    @Mock
    private PersonRepo personRepo;

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private FilmCommentsRepo filmCommentsRepo;

    @Mock
    private PersonCommentsRepo personCommentsRepo;

    @Mock
    private UserService userService;

    @Test
    public void filmCommentExistsTest() throws UserNotFoundException {
        Film film = getSimpleFilmWithSingleComment();

        Mockito.when(filmRepo.getFilmDetails(Mockito.anyLong())).thenReturn(Optional.of(film));
        Mockito.when(commentMapper.commentCommandToFilmCommentEntity(Mockito.any())).thenReturn(getFilmComment());

//        Mockito.when(userService.getCurrentlyLoggedUser()).thenReturn(new User());
//        Mockito.when(filmCommentsRepo.save(Mockito.any())).thenReturn(getFilmComment());
//        Mockito.when(filmRepo.save(Mockito.any())).thenReturn(getSimpleFilm());
//        Mockito.when(commentMapper.commentToCommentDTO(Mockito.any())).thenReturn(getCommentsDTO());

        commentService.addComment(getFilmCommentDTO());

        Assert.assertNotNull(film.getFilmComments().get(0));
    }

    @Test
    public void getPersonCommentExistsTest() {
        Person person = getPerson();

        Mockito.when(personRepo.getPersonDetails(Mockito.any())).thenReturn(Optional.of(person));

        Mockito.when(commentMapper.commentCommandToPersonCommandEntity(Mockito.any())).thenReturn(getPersonComment());
        Mockito.when(personCommentsRepo.save(Mockito.any())).thenReturn(getPersonComment());
        Mockito.when(personRepo.save(Mockito.any())).thenReturn(getPerson());

        commentService.addComment(getPersonCommentDTO());

        Assert.assertNotNull(person.getPersonComment().get(0));
    }


    private CommentCommand getFilmCommand() {
        CommentCommand commentCommand = new CommentCommand();
        commentCommand.setEntityType("FILM");

        commentCommand.setCommentsDTO(getFilmCommentDTO());

        return commentCommand;
    }

    private CommentCommand getPersonCommand() {
        CommentCommand commentCommand = new CommentCommand();
        commentCommand.setEntityType("PERSON");

        commentCommand.setCommentsDTO(getPersonCommentDTO());

        return commentCommand;
    }

    private CommentsDTO getPersonCommentDTO() {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setEntityType("PERSON");
        return commentsDTO;
    }

    private CommentsDTO getFilmCommentDTO() {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setText(COMMENT_TEXT);
        commentsDTO.setUser(new UserDTO());
        commentsDTO.setEntityId(FILM_ID);
        commentsDTO.setEntityType("FILM");
        return commentsDTO;
    }


}
