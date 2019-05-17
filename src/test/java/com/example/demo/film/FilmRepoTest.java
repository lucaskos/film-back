package com.example.demo.film;

import com.example.demo.DemoApplication;
import com.example.demo.application.model.Film;
import com.example.demo.application.repository.FilmRepo;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
    public void getFilmDetails(){
        Film film = filmRepo.getFilmDetails(1L).get();
        Assert.assertNotNull(film.getFilmComments().get(0).getId());
        Assert.assertNotNull(film.getFilmRelations().iterator().next().getId());
    }

    @Test(expected = LazyInitializationException.class)
    public void getSingleFilmWithComments_lazyException() {
        Film film = filmRepo.getOne(1L);
        Assert.assertNotNull(film.getFilmComments().get(0).getId());
    }

    @Test(expected = LazyInitializationException.class)
    public void getSingleFilmWithFilmRelation_lazyException() {
        Film film = filmRepo.getOne(1L);
        Assert.assertNotNull(film.getFilmRelations().iterator().next().getId());
    }

}
