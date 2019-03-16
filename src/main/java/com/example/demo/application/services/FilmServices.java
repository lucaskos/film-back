package com.example.demo.application.services;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.DTO.mapper.FilmMapper;
import com.example.demo.application.DTO.mapper.PersonMapper;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmRelations;
import com.example.demo.application.repository.FilmRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmServices {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private FilmRepo filmDao;
    private FilmMapper filmMapper;
    private PersonMapper personMapper;

    public FilmServices(FilmRepo filmDao, FilmMapper filmMapper, PersonMapper personMapper) {
        this.filmDao = filmDao;
        this.filmMapper = filmMapper;
        this.personMapper = personMapper;
    }

    public List<FilmDTO> getAllFilms() {
        List<FilmDTO> filmList = new ArrayList<>();
        filmDao.findAll().forEach(film -> filmList.add(filmMapper.filmToFilmDTO(film)));
        return filmList;
    }

    public FilmDTO getFilmById(Long id) {
        Film film = filmDao.findById(id).get();
        return filmMapper.filmToFilmDTO(film);
    }

    @PreAuthorize("hasAuthority('ADMIN') or ('EDITOR')")
    public FilmDTO addFilm(FilmDTO film) {
        Film save = filmDao.save(filmMapper.filmDTOToFilm(film));
        return filmMapper.filmToFilmDTO(save);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFilm(FilmDTO filmDTO) {
        logger.info("Deleting film: " + filmDTO.toString());
        //filmDao.delete(filmDao.findById(filmDTO.getFilmId()).get());
    }

    @Transactional
//    @PreAuthorize("hasAuthority('ADMIN') or ('EDITOR')")
    public FilmDTO updateFilm(FilmDTO film) {

        Film filmToUpdate;
        if (film.getFilmId() != null) {
            filmToUpdate = filmDao.getOne(film.getFilmId());
            film.setModificationDate(LocalDate.now());
        } else {
            filmToUpdate = filmMapper.filmDTOToFilm(film);
            filmToUpdate.setCreationDate(LocalDate.now());
            filmToUpdate.setModificationDate(LocalDate.now());
        }

        filmToUpdate.setFilmRelations(createAndUpdateFilmRelations(filmToUpdate, film));

        Film updatedFilm = filmDao.saveAndFlush(filmToUpdate);
        return filmMapper.filmToFilmDTO(updatedFilm);
    }

    public List<FilmDTO> getFilmsByTitle(String title) {
        List<FilmDTO> filmList = new ArrayList<>();
        filmDao.autocompleteByTitle(title).forEach(film -> filmList.add(filmMapper.filmToFilmDTO(film)));
        return filmList;
    }

    private void updateFilmWithValues(FilmDTO updatedFilm) {
        Film oldFilm = filmDao.findById(updatedFilm.getFilmId()).get();

    }

    private Set<FilmRelations> createAndUpdateFilmRelations(Film oldFilm, FilmDTO filmDTO) {
        final Set<FilmRelations> filmRelations = oldFilm.getFilmRelations();
        List<PersonDTO> peopleList = filmDTO.getPeopleList();

        Set<FilmRelations> filmRelationsAfterUpdate = new LinkedHashSet<>();

        peopleList.forEach(personDTO -> filmRelations.stream().forEach(filmRelation -> {
            if (filmRelation.getPerson() != null && personDTO.getId().equals(filmRelation.getPerson().getId())) {
                logger.info("Updating relation for person: " + personDTO.getId());
                filmRelation.setPerson(personMapper.personDTOToPerson(personDTO));
                filmRelationsAfterUpdate.add(filmRelation);
            } else {
                logger.info("Adding new relation for person: " + personDTO.getId());
                FilmRelations newRelation = new FilmRelations();
                newRelation.setPerson(personMapper.personDTOToPerson(personDTO));
                newRelation.setRole(personDTO.getRole());
                newRelation.setFilm(oldFilm);
                filmRelationsAfterUpdate.add(newRelation);
            }
        }));

        filmRelationsAfterUpdate.addAll(filmRelations);

        return filmRelationsAfterUpdate;
    }

}
