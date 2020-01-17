package com.luke.filmdb.film;

import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.DTO.mapper.EntityMapper;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.FilmRelations;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.repository.FilmRepo;
import com.luke.filmdb.application.services.FilmService;
import com.luke.filmdb.commons.FilmMapperCommons;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FilmServiceTest extends FilmMapperCommons {

    private final static String EDITED_ROLE = "EDITED_ROLE";
    private final static Long EDITED_FILM = 1L;
    private final static Long ADDITIONAL_PERSON = 2L;

    @InjectMocks
    private FilmService filmService;

    @Mock
    private FilmRepo filmRepo;

    @Mock
    private EntityMapper entityMapper;

    @Before
    public void init() {
    }

    @Test
    public void addSimpleFilm() {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle(FILM_TITLE);

        Film film = new Film();
        film.setTitle(FILM_TITLE);

        when(filmRepo.save(any(Film.class))).thenReturn(film);
        when(entityMapper.filmDTOToFilm(any(FilmDTO.class))).thenReturn(film);
        when(entityMapper.filmToFilmDTO(any(Film.class))).thenReturn(filmDTO);

        FilmDTO filmDTO1 = filmService.addFilm(filmDTO);

        Assert.assertEquals(film.getTitle(), filmDTO1.getTitle());
    }

    @Test
    public void saveSimpleFilm() {
        FilmDTO film = new FilmDTO();
        Film savedFilm = new Film();

        when(filmRepo.saveAndFlush(any(Film.class))).thenReturn(savedFilm);
        when(entityMapper.filmDTOToFilm(any(FilmDTO.class))).thenReturn(savedFilm);
        when(entityMapper.filmToFilmDTO(any(Film.class))).thenReturn(film);

        FilmDTO filmDTO = filmService.saveFilm(film).get();

        Assert.assertEquals(film, filmDTO);
    }

    @Test(expected = NotFoundException.class)
    public void getFilmByTitleAndThrowError() {
        List<FilmDTO> filmsByTitle = filmService.getFilmDTOByTitle(FILM_TITLE);

        Assert.assertNotNull(filmsByTitle);
    }

    @Test
    public void getFilmByTitle() {
        Film film = new Film();
        film.setTitle(FILM_TITLE);

        Optional<List<Film>> films = Optional.of(Collections.singletonList(film));

        Mockito.doReturn(films).when(filmRepo).findFilmsByTitleContainingIgnoreCase(FILM_TITLE);

        List<Film> filmsByTitle = filmService.getFilmsByTitle(FILM_TITLE);

        Assert.assertEquals(filmsByTitle.get(0).getTitle(), film.getTitle());

    }

    @Test
    public void saveSimpleFilmWithSingleRelation() {
        Film film = getFilm(FILM_ID);
        Film updatedRelations = getFilm(FILM_ID);

        FilmRelations filmRelations = new FilmRelations();
        Person person = getPerson();
        person.setId(PERSON_ID);
        filmRelations.setPerson(person);
        filmRelations.setFilm(updatedRelations.getFilmRelations().iterator().next().getFilm());
        updatedRelations.getFilmRelations().add(filmRelations);

        when(entityMapper.personDTOToPerson(getPersonDtoTest(PERSON_ID))).thenReturn(getPerson());
        when(entityMapper.filmToFilmDTO(any())).thenReturn(getFilmDTOAfterUpdate(FILM_ID));
        when(filmRepo.saveAndFlush(any())).thenReturn(updatedRelations);
        when(filmRepo.getOne(FILM_ID)).thenReturn(film);

        FilmDTO filmDTO = getSimpleDTOFilm();

        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDTO personDtoTest = getPersonDtoTest(PERSON_ID);
        personDtoTest.getFilmList().add(filmDTO);
        personDTOList.add(personDtoTest);

        filmDTO.setPeopleList(personDTOList);

        FilmDTO filmDTO1 = filmService.saveFilm(filmDTO).get();

        Assert.assertNotNull(filmDTO1.getPeopleList().get(0).getRole());
    }

    @Test
    public void filmWithEditedRelation() {

        when(entityMapper.filmToFilmDTO(any())).thenReturn(getFilmDTOAfterUpdate(EDITED_FILM));
        when(filmRepo.saveAndFlush(any())).thenReturn(getFilm(EDITED_FILM));
        when(filmRepo.getOne(EDITED_FILM)).thenReturn(getFilm(EDITED_FILM));
        when(entityMapper.personDTOToPerson(getPersonDtoTest(ADDITIONAL_PERSON))).thenReturn(getSecondPerson());

        FilmDTO filmDTO = getSimpleDTOFilm();
        filmDTO.setFilmId(EDITED_FILM);

        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDTO personDtoTest = getPersonDtoTest(PERSON_ID);
        personDtoTest.getFilmList().add(filmDTO);
        personDTOList.add(personDtoTest);
        personDTOList.get(0).setRole(EDITED_ROLE);

        filmDTO.setPeopleList(personDTOList);

        FilmDTO filmDTO1 = filmService.saveFilm(filmDTO).get();

        Assert.assertNotNull(filmDTO1.getPeopleList().get(0).getRole());
        Assert.assertTrue(filmDTO1.getPeopleList().get(0).getRole().equals(EDITED_ROLE));
    }

    @Test
    public void updateFilmWithAdditionalRelation() {
        Person person = getPerson();

        Film film = getFilm(FILM_ID);
        FilmRelations filmRelations = new FilmRelations();
        filmRelations.setFilm(film);
        filmRelations.setPerson(person);
        film.getFilmRelations().add(filmRelations);

        Person firstPerson = getPerson();
        Person secondPerson = getSecondPerson();

        when(filmRepo.saveAndFlush(any())).thenReturn(film);
        when(filmRepo.getOne(FILM_ID)).thenReturn(getFilm(FILM_ID));
        when(entityMapper.filmDTOToFilm(any())).thenReturn(getFilm(FILM_ID));

        FilmDTO filmDTO = getSimpleDTOFilm();

        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDTO personDtoTest = getPersonDtoTest(PERSON_ID);
        personDtoTest.getFilmList().add(filmDTO);
        personDTOList.add(personDtoTest);
        PersonDTO personDtoTestSecond = getPersonDtoTest(ADDITIONAL_PERSON);
        personDtoTestSecond.getFilmList().add(filmDTO);
        personDTOList.add(personDtoTestSecond);

        filmDTO.setPeopleList(personDTOList);

        when(entityMapper.personDTOToPerson(personDtoTest)).thenReturn(firstPerson);
        when(entityMapper.personDTOToPerson(personDtoTestSecond)).thenReturn(secondPerson);

        when(entityMapper.filmToFilmDTO(any())).thenReturn(filmDTO);

        FilmDTO filmDTO1 = filmService.saveFilm(filmDTO).get();

        Assert.assertEquals(filmDTO1.getPeopleList().get(0).getId(), PERSON_ID);
        Assert.assertEquals(filmDTO1.getPeopleList().get(1).getId(), ADDITIONAL_PERSON);
    }

    @Test
    public void deleteFilm() {
        Film film = getFilm(FILM_ID);

        when(filmRepo.findById(any())).thenReturn(Optional.of(film));

        filmService.deleteFilm(film.id);

        Mockito.verify(filmRepo, Mockito.times(1)).delete(film);
    }


    private Film getFilm(Long id) {
        Film film = Film.getFilmWithTitleYearDescription(FILM_TITLE, FILM_YEAR, FILM_DESCRIPTION);
        film.setId(id);

        FilmRelations filmRelation = new FilmRelations();
        filmRelation.setRole(PERSON_ROLE);
        filmRelation.setFilm(film);
        filmRelation.setPerson(getPerson());
        Set<FilmRelations> filmRelations = new HashSet<>();
        filmRelations.add(filmRelation);

        film.setFilmRelations(filmRelations);

        return film;
    }

    private Person getSecondPerson() {
        Person person = getPerson();
        person.setId(ADDITIONAL_PERSON);
        return person;
    }

    private FilmDTO getFilmDTOAfterUpdate(Long id) {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle(FILM_TITLE);
        filmDTO.setDescription(FILM_DESCRIPTION);
        filmDTO.setYear(FILM_YEAR);
        filmDTO.setFilmId(id);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(PERSON_ID);
        personDTO.setLastName(PERSON_LAST_NAME);
        personDTO.setFirstName(PERSON_FIRST_NAME);
        personDTO.setBiography(PERSON_BIOGRAPHY);

        //simple film
        if (id.equals(FILM_ID)) {
            personDTO.setRole(PERSON_ROLE);
        } else if (id.equals(EDITED_FILM)) {
            personDTO.setRole(EDITED_ROLE);
        }

        filmDTO.setPeopleList(Arrays.asList(personDTO));

        return filmDTO;
    }

    @Test
    public void getAllFilms() {
        when(filmRepo.findAll()).thenReturn(Collections.singletonList(getSimpleFilm()));

        List<FilmDTO> allFilms = filmService.getAllFilms();

        Assert.assertTrue(allFilms.size() > 0);
    }

    @Test
    public void getFilmDetails() {
        when(filmRepo.getFilmDetails(FILM_ID)).thenReturn(Optional.ofNullable(getSimpleFilm()));
        when(entityMapper.filmToFilmDTO(getSimpleFilm())).thenReturn(getSimpleDTOFilm());

        FilmDTO filmDetails = filmService.getFilmDetails(FILM_ID);

        Assert.assertNotNull(filmDetails);
    }
}
