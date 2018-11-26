package com.example.demo;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.mapper.FilmMapper;
import com.example.demo.application.model.Film;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmMapperTest extends FilmMapperCommons{

    @Autowired
    private FilmMapper mapper;

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
}
