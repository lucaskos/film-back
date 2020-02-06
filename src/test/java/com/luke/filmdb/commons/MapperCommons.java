package com.luke.filmdb.commons;

import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.Person;

import java.time.LocalDate;

public abstract class MapperCommons {

    protected static final LocalDate MODIFICATION_DATE = LocalDate.of(1900, 01, 01);
    protected static final LocalDate CREATION_DATE = LocalDate.of(1901, 01, 01);

    public static final Long FILM_ID = 9999L;
    public static final String FILM_TITLE = "FILM_TITLE";
    protected static final String FILM_DESCRIPTION = "FILM_DESCRIPTION";
    protected static int FILM_YEAR = 2000;

    protected static final String PERSON_ROLE = "PERSON_ROLE";
    protected static final String PERSON_BIOGRAPHY = "PERSON_BIOGRAPHY";
    //    protected static final Date BORN_DATE = Date.from()
    //    protected static final DATE PERSON_BIOGRAPHY = "PERSON_BIOGRAPHY";
    public static final String PERSON_FIRST_NAME = "PERSON_FIRST_NAME";
    protected static final String PERSON_LAST_NAME = "PERSON_LAST_NAME";
    public static final Long PERSON_ID = 1L;
    protected static final LocalDate PERSON_BORN_DATE = LocalDate.of(2000, 01, 01);
    protected static final LocalDate PERSON_DIED_DATE = LocalDate.of(2100, 01, 01);

    public static FilmDTO getSimpleDTOFilm() {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle(FILM_TITLE);
        filmDTO.setFilmId(FILM_ID);
        filmDTO.setCreationDate(CREATION_DATE);
        filmDTO.setModificationDate(MODIFICATION_DATE);
        filmDTO.setYear(FILM_YEAR);
        filmDTO.setDescription(FILM_DESCRIPTION);

        return filmDTO;
    }

    public static PersonDTO getPersonDtoTest() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(PERSON_ID);
        personDTO.setRole(PERSON_ROLE);
        personDTO.setBiography(PERSON_BIOGRAPHY);
        personDTO.setBornDate(PERSON_BORN_DATE);
        personDTO.setDiedDate(PERSON_DIED_DATE);
        personDTO.setCreationDate(CREATION_DATE);
        personDTO.setModificationDate(MODIFICATION_DATE);
        personDTO.setFirstName(PERSON_FIRST_NAME);
        personDTO.setLastName(PERSON_LAST_NAME);

        return personDTO;
    }

    public static Film getSimpleFilm() {
        Film film = new Film();
        film.setTitle(FILM_TITLE);
        film.setCreationDate(CREATION_DATE);
        film.setModificationDate(MODIFICATION_DATE);
        film.setYear(FILM_YEAR);
        film.setDescription(FILM_DESCRIPTION);
        return film;
    }

    public Person getPerson() {
        Person person = new Person();
        person.setFirstName(PERSON_FIRST_NAME);
        person.setLastName(PERSON_LAST_NAME);
        person.setId(PERSON_ID);
        person.setBornDate(PERSON_BORN_DATE);
        person.setDiedDate(PERSON_DIED_DATE);
        person.setModificationDate(MODIFICATION_DATE);
        person.setCreationDate(CREATION_DATE);
        return person;
    }
}
