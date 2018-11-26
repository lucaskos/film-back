package com.example.demo.application.services;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.mapper.FilmMapper;
import com.example.demo.application.model.Film;
import com.example.demo.application.repository.FilmRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FilmServices {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private FilmRepo filmDao;
    private FilmMapper mapper;

    public FilmServices(FilmRepo filmDao, FilmMapper mapper) {
        this.filmDao = filmDao;
        this.mapper = mapper;
    }

    public List<FilmDTO> getAllFilms() {
        List<FilmDTO> filmList = new ArrayList<>();
        filmDao.findAll().forEach(film -> filmList.add(mapper.filmToFilmDTO(film)));
        return filmList;
    }

    public FilmDTO getFilmById(Long id) {
        Film film = filmDao.findById(id).get();
        logger.info("SINGLE FILM : " + film);
        return mapper.filmToFilmDTO(film);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FilmDTO addFilm(FilmDTO film) {
        Film save = filmDao.save(mapper.filmDTOToFilm(film));
        return mapper.filmToFilmDTO(save);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteFilm(FilmDTO filmDTO) {
        filmDao.delete(filmDao.findById(filmDTO.getFilmId()).get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FilmDTO updateFilm(Film film) {
        //fixme
//        film.setModificationDate(new Date());
        Film updatedFilm = filmDao.saveAndFlush(film);
        return mapper.filmToFilmDTO(updatedFilm);
    }

}
