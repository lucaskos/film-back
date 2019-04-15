package com.example.demo;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;

import java.time.LocalDate;

public abstract class FilmMapperCommons {

    protected static final Long FILM_ID = 9999L;
    protected static final String FILM_TITLE = "FILM_TITLE";
    protected static final LocalDate FILM_CREATION_DATE = LocalDate.of(2000,01,01);
    protected static final LocalDate FILM_MODIFICATION_DATE = LocalDate.of(2000, 01, 01);
    protected static int FILM_YEAR = 2000;
    protected static final String FILM_DESCRIPTION = "FILM_DESCRIPTION";

    protected static final String PERSON_ROLE = "PERSON_ROLE";
    protected static final String PERSON_BIOGRAPHY = "PERSON_BIOGRAPHY";
//    protected static final Date BORN_DATE = Date.from()
//    protected static final DATE PERSON_BIOGRAPHY = "PERSON_BIOGRAPHY";
    protected static final String PERSON_FIRST_NAME = "PERSON_FIRST_NAME";
    protected static final String PERSON_LAST_NAME = "PERSON_LAST_NAME";
    protected static final Long PERSON_ID = 1L;


    public FilmDTO getSimpleTestFilm() {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle(FILM_TITLE);
        filmDTO.setFilmId(FILM_ID);
        filmDTO.setCreationDate(FILM_CREATION_DATE);
        filmDTO.setModificationDate(FILM_MODIFICATION_DATE);
        filmDTO.setYear(FILM_YEAR);
        filmDTO.setDescription(FILM_DESCRIPTION);

        return filmDTO;
    }

    public PersonDTO getPersonDtoTest(Long personId) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setRole(PERSON_ROLE);
        personDTO.setBiography(PERSON_BIOGRAPHY);
//        personDTO.setBornDate();
        personDTO.setFirstName(PERSON_FIRST_NAME);
        personDTO.setLastName(PERSON_LAST_NAME);
        personDTO.setId(personId);

        return personDTO;
    }
}
