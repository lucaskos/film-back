//package com.example.demo;
//
//import com.example.demo.application.model.Film;
//import com.example.demo.application.model.FilmRelations;
//import com.example.demo.application.model.Person;
//import com.example.demo.application.model.dictionaries.PersonRole;
//import com.example.demo.application.repository.FilmRepo;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.simpleFilmWithSingleRelation.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.simpleFilmWithSingleRelation.context.SpringBootTest;
//import org.springframework.simpleFilmWithSingleRelation.context.junit4.SpringRunner;
//
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@SpringBootTest
//public class FilmRepoTest {
//
//    @Autowired
//    private FilmRepo filmRepo;
//
//    private final static String FILM_ROLE_TEST = "TEST";
//
//    @Test
//    public void insertFilm() {
//        Film film = new Film();
//        film.setTitle("TITLE");
//
//        filmRepo.save(film);
//
//        Film saved = filmRepo.getByTitle("TITLE");
//
//        Assert.assertNotNull(saved);
//
//        Set<FilmRelations> filmRelationsSet = new LinkedHashSet<>();
//        FilmRelations filmRelations = new FilmRelations();
//        filmRelations.setPerson(getPerson());
//        filmRelations.setPersonRoleDictionary(new PersonRole());
//        filmRelations.setRole(FILM_ROLE_TEST);
//        filmRelationsSet.add(filmRelations);
//        film.setFilmRelations(filmRelationsSet);
//
//        Film savedFilmWithRelations = filmRepo.save(film);
//
//        Assert.assertNotNull(savedFilmWithRelations.getFilmRelations());
//        Assert.assertNotNull(savedFilmWithRelations.getFilmRelations().iterator().next().getPerson());
//        Assert.assertEquals(savedFilmWithRelations.getFilmRelations().iterator().next().getRole(), FILM_ROLE_TEST);
//
//    }
//
//    @Test
//    public void updateFilm() {
//        Film filmBeforeUpdate = filmRepo.findAll().get(0);
//
//        Film filmToUpdate = filmRepo.findAll().get(0);
//        filmToUpdate.setTitle("NEW_TITLE");
//
//        Film saved = filmRepo.save(filmToUpdate);
//
//        Film filmAfterUpdate = filmRepo.getOne(filmBeforeUpdate.getId());
//
//        Assert.assertEquals(filmAfterUpdate.getId(), saved.getId());
//        Assert.assertEquals(filmAfterUpdate.getTitle(), "NEW_TITLE");
//        Assert.assertNotEquals(filmBeforeUpdate.getTitle(), "NEW_TTTLE");
//    }
//
//    @Test
//    public void check() {
//        List<Film> all = filmRepo.findAll();
//
//        Assert.assertNotNull(all.get(0));
//    }
//
//    private Person getPerson() {
//        Person person = new Person();
//        person.setBio("BIO");
//        person.setFirstName("PERSON_FIRST_NAME");
//        person.setLastName("PERSON_LAST_NAME");
//
//        return person;
//    }
//
//}
