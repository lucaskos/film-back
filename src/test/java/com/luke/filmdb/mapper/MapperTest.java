package com.luke.filmdb.mapper;

import com.luke.filmdb.application.DTO.CommentDTO;
import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.DTO.mapper.EntityMapper;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.commons.CommentsCommon;
import com.luke.filmdb.commons.MapperCommons;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest extends MapperCommons {

    @Autowired
    private EntityMapper entityMapper;

    @Test
    public void mapFilmDtoToFilm() {

        FilmDTO filmDTO = getSimpleDTOFilm();

        Film film = entityMapper.filmDTOToFilm(filmDTO);

        Assert.assertEquals(filmDTO.getFilmId(), film.getId());
        Assert.assertEquals(filmDTO.getTitle(), film.getTitle());
        Assert.assertEquals(filmDTO.getCreationDate(), film.getCreationDate());
        Assert.assertEquals(filmDTO.getModificationDate(), film.getModificationDate());
        Assert.assertEquals(filmDTO.getDescription(), film.getDescription());
        Assert.assertEquals(filmDTO.getDescription(), film.getDescription());
    }

    @Test
    public void mapFilmToFilmDTO() {
        Film film = getSimpleFilm();

        FilmDTO filmDTO = entityMapper.filmToFilmDTO(film);

        Assert.assertEquals(filmDTO.getFilmId(), film.getId());
        Assert.assertEquals(filmDTO.getTitle(), film.getTitle());
        Assert.assertEquals(filmDTO.getCreationDate(), film.getCreationDate());
        Assert.assertEquals(filmDTO.getModificationDate(), film.getModificationDate());
        Assert.assertEquals(filmDTO.getDescription(), film.getDescription());
        Assert.assertEquals(filmDTO.getDescription(), film.getDescription());
    }

    @Test
    public void mapPersonDtoToPersonAndAssertEquals() {
        PersonDTO personDTO = getPersonDtoTest();

        Person person = entityMapper.personDTOToPerson(personDTO);

        Assert.assertEquals(personDTO.getFirstName(), person.getFirstName());
        Assert.assertEquals(personDTO.getId(), person.getId());
        Assert.assertEquals(personDTO.getModificationDate(), person.getModificationDate());
        Assert.assertEquals(personDTO.getCreationDate(), person.getCreationDate());
    }


    //todo mapping like that cannot be done person <-> film
    @Test
    public void mapFilmDtoWithRelations() {
        FilmDTO filmDTO = getSimpleDTOFilm();

        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDTO personDtoTest = getPersonDtoTest();
        personDtoTest.getFilmList().add(filmDTO);
        personDTOList.add(personDtoTest);

        filmDTO.setPeopleList(personDTOList);

        Assert.assertNotNull(filmDTO.getPeopleList());
        Film film = entityMapper.filmDTOToFilm(filmDTO);

        Assert.assertNotNull(film.getFilmRelations());
    }

    @Test
    public void mapFilmDtoWithComments() {
        FilmDTO filmDTO = getSimpleDTOFilm();

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCreatedDate(LocalDate.now());
        commentDTO.setText("TEXT");
        commentDTO.setTitle("TITLE");
        commentDTO.setEntityId(FILM_ID);
        commentDTO.setUser(new UserDTO());
        commentDTO.setId(1L);

        filmDTO.getFilmCommentsList().add(commentDTO);

        Film film = entityMapper.filmDTOToFilm(filmDTO);

        Assert.assertNotNull(film.getFilmComments());
        Assert.assertEquals(film.getFilmComments().get(0).getId(), Long.valueOf(filmDTO.getFilmCommentsList().get(0).getId()));
    }


    @Test
    public void commentToFilmCommentDTO() {
        FilmDTO simpleDTOFilm = getSimpleDTOFilm();
        simpleDTOFilm.setFilmCommentsList(Collections.singletonList(CommentsCommon.getCommentsDTO()));

        Film film = entityMapper.filmDTOToFilm(simpleDTOFilm);

        Assert.assertEquals(simpleDTOFilm.getFilmCommentsList().get(0).getText(), film.getFilmComments().get(0).getText());
    }

    @Test
    public void personToPersonDTOMapping() {
        Person person = getPerson();

        PersonDTO mappedPersonDTO = entityMapper.personToPersonDTO(person);

        Assert.assertEquals(person.getBio(), mappedPersonDTO.getBiography());
    }

    @Test
    public void mapFilmDtoToFilmWithFilmComments() {
        Film simpleFilm = getSimpleFilm();

        FilmComment filmComment = new FilmComment();
        filmComment.setFilm(simpleFilm);
        filmComment.setId(1L);
        filmComment.setText("TEXT");
        filmComment.setTitle("TITLE");

        simpleFilm.getFilmComments().add(filmComment);

        FilmDTO filmDTO = entityMapper.filmToFilmDTO(simpleFilm);

        Assert.assertEquals(filmDTO.getFilmId(), simpleFilm.getId());
        Assert.assertEquals(filmDTO.getFilmCommentsList().get(0).getId(), simpleFilm.getFilmComments().get(0).getId());
    }
}
