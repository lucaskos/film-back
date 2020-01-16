package com.luke.filmdb.comments;

import com.luke.filmdb.application.DTO.CommentDTO;
import com.luke.filmdb.application.DTO.mapper.CommentMapper;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

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

        when(filmRepo.getFilmDetails(Mockito.anyLong())).thenReturn(Optional.of(film));
        when(commentMapper.commentCommandToFilmCommentEntity(Mockito.any())).thenReturn(getFilmComment());

        commentService.addComment(getFilmCommentDTO());

        Assert.assertNotNull(film.getFilmComments().get(0));
    }

    @Test
    public void getPersonCommentExistsTest() {
        Person person = getPerson();

        when(personRepo.getPersonDetails(Mockito.any())).thenReturn(Optional.of(person));

        when(commentMapper.commentCommandToPersonCommandEntity(Mockito.any())).thenReturn(getPersonComment());
        when(personCommentsRepo.save(Mockito.any())).thenReturn(getPersonComment());
        when(personRepo.save(Mockito.any())).thenReturn(getPerson());

        commentService.addComment(getPersonCommentDTO());

        Assert.assertNotNull(person.getPersonComment().get(0));
    }

    @Test
    public void getSingleFilmComment() {
        CommentDTO commentDTO = getFilmCommentDTO();

        when(filmCommentsRepo.findByFilmId(FILM_ID)).thenReturn(Optional.of(Collections.singletonList(getFilmComment())));
        when(commentMapper.commentToCommentDTO(getFilmComment())).thenReturn(getCommentsDTO());

        List<CommentDTO> entityComments = commentService.findEntityComments(commentDTO);

        Assert.assertTrue(entityComments.size() > 0);
        Assert.assertEquals(getFilmComment().getText(), entityComments.get(0).getText());
    }

    @Test
    public void getSinglePersonComment() {
        CommentDTO commentDTO = getPersonCommentDTO();

        when(personCommentsRepo.findByPersonId(PERSON_ID)).thenReturn(Optional.of(Collections.singletonList(getPersonComment())));
        when(commentMapper.commentToCommentDTO(getPersonComment())).thenReturn(getCommentsDTO());

        List<CommentDTO> entityComments = commentService.findEntityComments(commentDTO);

        Assert.assertTrue(entityComments.size() > 0);
        Assert.assertEquals(getFilmComment().getText(), entityComments.get(0).getText());
    }


}
