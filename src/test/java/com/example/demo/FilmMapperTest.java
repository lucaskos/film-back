package com.example.demo;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.DTO.mapper.FilmMapper;
import com.example.demo.application.DTO.mapper.PersonMapper;
import com.example.demo.application.model.Film;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmMapperTest extends FilmMapperCommons{

    @Autowired
    private FilmMapper mapper;

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void mapFilm() {

        FilmDTO filmDTO = getTestFilm();

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
    public void mapFilmWithRelations() {
        FilmDTO filmDTO = getTestFilm();

        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDTO personDtoTest = getPersonDtoTest(PERSON_ID);
        personDtoTest.getFilmList().add(filmDTO);
        personDTOList.add(personDtoTest);

        filmDTO.setPeopleList(personDTOList);

        Assert.assertNotNull(filmDTO.getPeopleList());
        Film film = mapper.filmDTOToFilm(filmDTO);

        Assert.assertNotNull(film.getFilmRelations());
    }
}
