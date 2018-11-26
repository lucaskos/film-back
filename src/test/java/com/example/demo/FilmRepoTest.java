package com.example.demo;

import com.example.demo.application.model.Film;
import com.example.demo.application.model.Person;
import com.example.demo.application.repository.FilmRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class FilmRepoTest {

    @Autowired
    private FilmRepo filmRepo;

    @Test
    public void insertFilm() {
        Film film = new Film();
        film.setTitle("TITLE");

        filmRepo.save(film);

        Film saved = filmRepo.getByTitle("TITLE");

        Assert.assertNotNull(saved);
    }

    @Test
    public void updateFilm() {
        Film filmBeforeUpdate = filmRepo.findAll().get(0);

        Film filmToUpdate = filmRepo.findAll().get(0);
        filmToUpdate.setTitle("NEW_TITLE");

        Film saved = filmRepo.save(filmToUpdate);

        Film filmAfterUpdate = filmRepo.getOne(filmBeforeUpdate.getId());

        Assert.assertEquals(filmAfterUpdate.getId(), saved.getId());
        Assert.assertEquals(filmAfterUpdate.getTitle(), "NEW_TITLE");
        Assert.assertNotEquals(filmBeforeUpdate.getTitle(), "NEW_TTTLE");
    }

    @Test
    public void check() {
        List<Film> all = filmRepo.findAll();

        Assert.assertNotNull(all.get(0));
    }

    private Person getPerson() {
        Person person = new Person();
        person.setBio("BIO");
        person.setFirstName("FIRST_NAME");
        person.setLastName("LAST_NAME");

        return person;
    }

}
