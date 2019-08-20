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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('EDITOR')")
    public FilmDTO addFilm(FilmDTO film) {
        Film simpleFilm = filmMapper.filmDTOToFilm(film);
        Film save = filmDao.save(simpleFilm);
        return filmMapper.filmToFilmDTO(save);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFilm(Long id) {
        logger.info("Deleting film: " + id);
        Film film = filmDao.findById(id).get();
        Set<FilmRelations> filmRelations = film.getFilmRelations();
        film.getFilmRelations().removeAll(filmRelations);
        filmDao.save(film);
        filmDao.delete(film);
    }

    @PreAuthorize("hasRole('ADMIN') || hasAuthority('can_write')")
    public Optional<FilmDTO> saveFilm(FilmDTO film) {

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
        return Optional.of(filmMapper.filmToFilmDTO(updatedFilm));
    }

    public List<Film> getFilmsByTitle(String title) {
        String trimmedString = title.trim();
        return filmDao.findFilmsByTitleContainingIgnoreCase(trimmedString)
                .orElseThrow(() -> new NotFoundException("For string: " + title + " , nothing has been found."));
    }

    public List<FilmDTO> mapFilmListToFilmDTOList(List<Film> list) {
        return list.stream().map(film -> filmMapper.filmToFilmDTO(film)).collect(Collectors.toList());
    }

    public List<FilmDTO> getFilmDTOByTitle(String title) {
        List<Film> filmsByTitle = getFilmsByTitle(title);
        return mapFilmListToFilmDTOList(filmsByTitle);
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
