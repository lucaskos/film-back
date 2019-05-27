package com.example.demo.application.services;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.DTO.mapper.FilmMapper;
import com.example.demo.application.DTO.mapper.PersonMapper;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmRelations;
import com.example.demo.application.repository.FilmRepo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private FilmRepo filmDao;
    private FilmMapper filmMapper;
    private PersonMapper personMapper;

    public FilmService(FilmRepo filmDao, FilmMapper filmMapper, PersonMapper personMapper) {
        this.filmDao = filmDao;
        this.filmMapper = filmMapper;
        this.personMapper = personMapper;
    }

    public List<FilmDTO> getAllFilms() {
        List<FilmDTO> filmList = new ArrayList<>();
        filmDao.findAll().forEach(film -> filmList.add(filmMapper.filmToFilmDTO(film)));
        return filmList;
    }

    public FilmDTO getFilmDetails(Long id) {
        Film film = filmDao.getFilmDetails(id).orElseThrow(() -> new NotFoundException("Film not found id: " + id));
        FilmDTO filmDTO = filmMapper.filmToFilmDTO(film);
        return filmDTO;
    }

    //    @PreAuthorize("hasAuthority('ADMIN') or ('EDITOR')")
    public FilmDTO addFilm(FilmDTO film) {
        Film save = filmDao.save(filmMapper.filmDTOToFilm(film));
        return filmMapper.filmToFilmDTO(save);
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFilm(Long id) {
        logger.info("Deleting film: " + id);
        Film film = filmDao.findById(id).get();
        Set<FilmRelations> filmRelations = film.getFilmRelations();
        film.getFilmRelations().removeAll(filmRelations);
        filmDao.save(film);
        filmDao.delete(film);
    }

    @PreAuthorize("hasRole('ADMIN') || hasAuthority('can_write')")
    public FilmDTO saveFilm(FilmDTO film) {

        Film filmToUpdate;
        if (film.getFilmId() != null) {
            filmToUpdate = filmDao.getOne(film.getFilmId());
            film.setModificationDate(LocalDate.now());
        } else {
            filmToUpdate = filmMapper.filmDTOToFilm(film);
//            filmToUpdate.setCreationDate(LocalDate.now());
            filmToUpdate.setModificationDate(LocalDate.now());
        }

        filmToUpdate.setFilmRelations(createAndUpdateFilmRelations(filmToUpdate, film));

        Film film1 = filmMapper.filmDTOToFilm(film);

        film1.setFilmRelations(filmToUpdate.getFilmRelations());
        film1.setFilmComments(filmToUpdate.getFilmComments());

        Film updatedFilm = filmDao.saveAndFlush(film1);
        return filmMapper.filmToFilmDTO(updatedFilm);
    }

    public List<FilmDTO> getFilmsByTitle(String title) {
        String trimmedString = title.trim();
        List<Film> list = filmDao.findFilmsByTitleContainingIgnoreCase(trimmedString)
                .orElseThrow(() -> new NotFoundException("For string: " + title + " , nothing has been found."));
        List<FilmDTO> filmDTOList = new ArrayList<>();
        list.stream().forEach(film -> filmDTOList.add(filmMapper.filmToFilmDTO(film)));
        return filmDTOList;
    }

    private Set<FilmRelations> createAndUpdateFilmRelations(Film oldFilm, FilmDTO filmDTO) {
        final Set<FilmRelations> filmRelations = oldFilm.getFilmRelations();
        List<PersonDTO> peopleList = filmDTO.getPeopleList();

        Set<FilmRelations> filmRelationsAfterUpdate = new LinkedHashSet<>();

        if (!CollectionUtils.isEmpty(peopleList)) {
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
        }

        filmRelationsAfterUpdate.addAll(filmRelations);

        return filmRelationsAfterUpdate;
    }

}
