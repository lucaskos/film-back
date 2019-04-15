package com.example.demo.film;

import com.example.demo.DemoApplication;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmComments;
import com.example.demo.application.model.FilmRelations;
import com.example.demo.application.repository.FilmRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

//
@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(classes = DemoApplication.class)
public class FilmRepoTest {

    @Autowired
    private FilmRepo filmRepo;

    @Test
    public void getSingleFilmThenOk(){
        Film one = filmRepo.getOne(Long.valueOf(1));

        Assert.assertEquals(Long.valueOf(1), one.getId());
    }

    @Test
    public void getAllFilms(){
        List<Film> filmList = filmRepo.findAll();
        List<FilmComments> filmComments = filmList.get(0).getFilmComments();
        Set<FilmRelations> filmRelations = filmList.get(0).getFilmRelations();
        Assert.assertTrue(filmList.size() > 0);
    }

}
