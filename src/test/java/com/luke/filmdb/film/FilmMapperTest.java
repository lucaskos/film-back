package com.luke.filmdb.film;

import com.luke.filmdb.application.DTO.CommentsDTO;
import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.DTO.UserDTO;
import com.luke.filmdb.application.DTO.mapper.FilmMapper;
import com.luke.filmdb.application.DTO.mapper.PersonMapper;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.commons.FilmMapperCommons;
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
		commentsDTO.setText("TEXT");
		commentsDTO.setTitle("TITLE");
		commentsDTO.setEntityId(FILM_ID);
		commentsDTO.setUser(new UserDTO());
		commentsDTO.setId(1L);

		filmDTO.getFilmCommentsList().add(commentsDTO);

		Film film = mapper.filmDTOToFilm(filmDTO);

//		Assert.assertNotNull(film.getFilmComments());
//		Assert.assertEquals(film.getFilmComments().get(0).getId(), Long.valueOf(1L));
	}

	@Test
	public void mapFilmDtoToFilm() {
		Film simpleFilm = getSimpleFilm();

		FilmDTO filmDTO = mapper.filmToFilmDTO(simpleFilm);

		Assert.assertEquals(filmDTO.getFilmId(), filmDTO.getFilmId());
	}

//	@Test
//	public void mapFilmDtoToFilmWithComments() {
//		Film simpleFilm = getSimpleFilm();
//
//		FilmComment filmComment = new FilmComment();
//		filmComment.setFilm(simpleFilm);
//		filmComment.setId(1L);
//		filmComment.setText("TEXT");
//		filmComment.setDepth(0);
//		filmComment.setTitle("TITLE");
//
//		simpleFilm.getFilmComments().add(filmComment);
//
//		FilmDTO filmDTO = mapper.filmToFilmDTO(simpleFilm);
//
//		Assert.assertEquals(filmDTO.getFilm(), simpleFilm.getId());
//		Assert.assertEquals(filmDTO.getFilmCommentsList().get(0).getId(), simpleFilm.getFilmComments().get(0).getId());
//	}
}
