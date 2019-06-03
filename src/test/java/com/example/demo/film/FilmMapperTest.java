package com.example.demo.film;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.DTO.mapper.FilmMapper;
import com.example.demo.application.DTO.mapper.PersonMapper;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmComment;
import com.example.demo.commons.FilmMapperCommons;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmMapperTest extends FilmMapperCommons {

	@Autowired
	private FilmMapper mapper;

	@Autowired
	private PersonMapper personMapper;

	@Test
	public void mapFilmDto() {

		FilmDTO filmDTO = getSimpleTestFilm();

		Film film = mapper.filmDTOToFilm(filmDTO);

		Assert.assertEquals(filmDTO.getFilmId(), film.getId());
		Assert.assertEquals(filmDTO.getTitle(), film.getTitle());
		Assert.assertEquals(filmDTO.getCreationDate(), film.getCreationDate());
		Assert.assertEquals(filmDTO.getModificationDate(), film.getCreationDate());
		Assert.assertEquals(filmDTO.getDescription(), film.getDescription());
		Assert.assertEquals(filmDTO.getDescription(), film.getDescription());
	}


	//todo mapping like that cannot be done person <-> film
	@Test
	public void mapFilmDtoWithRelations() {
		FilmDTO filmDTO = getSimpleTestFilm();

		List<PersonDTO> personDTOList = new ArrayList<>();
		PersonDTO personDtoTest = getPersonDtoTest(PERSON_ID);
		personDtoTest.getFilmList().add(filmDTO);
		personDTOList.add(personDtoTest);

		filmDTO.setPeopleList(personDTOList);

		Assert.assertNotNull(filmDTO.getPeopleList());
		Film film = mapper.filmDTOToFilm(filmDTO);

		Assert.assertNotNull(film.getFilmRelations());
	}

	@Test
	public void mapFilmDtoWithComments() {
		FilmDTO filmDTO = getSimpleTestFilm();

		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setCreatedDate(LocalDate.now());
		commentsDTO.setDepth(0);
		commentsDTO.setText("TEXT");
		commentsDTO.setTitle("TITLE");
		commentsDTO.setEntityId(FILM_ID);
		commentsDTO.setUserId(new UserDTO());
		commentsDTO.setId(1L);

		filmDTO.getFilmCommentsList().add(commentsDTO);

		Film film = mapper.filmDTOToFilm(filmDTO);

		Assert.assertNotNull(film.getFilmComments());
		Assert.assertEquals(film.getFilmComments().get(0).getId(), Long.valueOf(1L));
	}

	@Test
	public void mapFilmDtoToFilm() {
		Film simpleFilm = getSimpleFilm();

		FilmDTO filmDTO = mapper.filmToFilmDTO(simpleFilm);

		Assert.assertEquals(filmDTO.getFilmId(), filmDTO.getFilmId());
	}

	@Test
	public void mapFilmDtoToFilmWithComments() {
		Film simpleFilm = getSimpleFilm();

		FilmComment filmComment = new FilmComment();
		filmComment.setFilmId(simpleFilm);
		filmComment.setId(1L);
		filmComment.setText("TEXT");
		filmComment.setDepth(0);
		filmComment.setTitle("TITLE");

		simpleFilm.getFilmComments().add(filmComment);

		FilmDTO filmDTO = mapper.filmToFilmDTO(simpleFilm);

		Assert.assertEquals(filmDTO.getFilmId(), simpleFilm.getId());
		Assert.assertEquals(filmDTO.getFilmCommentsList().get(0).getId(), simpleFilm.getFilmComments().get(0).getId());
	}
}
