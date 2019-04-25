package com.example.demo.film;

import com.example.demo.DemoApplication;
import com.example.demo.application.model.Film;
import com.example.demo.application.repository.FilmRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

        Film film = filmRepo.getFilmDetails(1L);
        Assert.assertNotNull(film.getFilmComments().get(0).getId());
        Assert.assertNotNull(film.getFilmRelations());
//        List<FilmComments> allComments = filmRepo.getFilmDetails(film.getId());
//        List<FilmComments> filmComments = film.getFilmComments();
//        Set<FilmRelations> filmRelations = film.getFilmRelations();

//        Assert.assertNotNull(filmComments.get(0).getId());
//        Assert.assertTrue(filmRelations.size() > 0);
    }

}
