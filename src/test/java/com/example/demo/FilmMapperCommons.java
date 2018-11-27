package com.example.demo;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.model.Film;

import java.time.LocalDate;

public abstract class FilmMapperCommons {

    protected static final Long FILM_ID = 9999L;
    protected static final String FILM_TITLE = "FILM_TITLE";
    protected static final LocalDate CREATION_DATE = LocalDate.of(2000,01,01);
    protected static final LocalDate MODIFICATION_DATE = LocalDate.of(2000, 01, 01);
    protected static int YEAR = 2000;
    protected static final String DESCRIPTION = "DESCRIPTION";


    public FilmDTO getTestFilm() {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle(FILM_TITLE);
        filmDTO.setFilmId(FILM_ID);
        filmDTO.setCreationDate(CREATION_DATE);
        filmDTO.setModificationDate(MODIFICATION_DATE);
        filmDTO.setYear(YEAR);
        filmDTO.setDescription(DESCRIPTION);

        return filmDTO;
    }
}
