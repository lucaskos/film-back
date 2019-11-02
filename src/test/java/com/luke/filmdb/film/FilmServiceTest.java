package com.luke.filmdb.film;

import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.DTO.mapper.FilmMapper;
import com.luke.filmdb.application.DTO.mapper.PersonMapper;
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

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FilmServiceTest extends FilmMapperCommons {

    private final static String EDITED_ROLE = "EDITED_ROLE";
    private final static Long EDITED_FILM = 1L;
    private final static Long ADDITIONAL_PERSON = 2L;
    private final static Long ADDITIONAL_FILM_ID = 2L;

    @InjectMocks
    private FilmService filmService;

    @Mock
    private FilmRepo filmRepo;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private FilmMapper filmMapper;

    @Before
    public void init() {
    }

    @Test
    public void addSimpleFilm() {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle(FILM_TITLE);

        Film film = new Film();
        film.setTitle(FILM_TITLE);

        Mockito.when(filmRepo.save(any(Film.class))).thenReturn(film);
        Mockito.when(filmMapper.filmDTOToFilm(any(FilmDTO.class))).thenReturn(film);
        Mockito.when(filmMapper.filmToFilmDTO(any(Film.class))).thenReturn(filmDTO);

        FilmDTO filmDTO1 = filmService.addFilm(filmDTO);

        Assert.assertEquals(film.getTitle(), filmDTO1.getTitle());
    }

    @Test
    public void saveSimpleFilm() {
        FilmDTO film = new FilmDTO();
        Film savedFilm = new Film();

        Mockito.when(filmRepo.saveAndFlush(any(Film.class))).thenReturn(savedFilm);
        Mockito.when(filmMapper.filmDTOToFilm(any(FilmDTO.class))).thenReturn(savedFilm);
        Mockito.when(filmMapper.filmToFilmDTO(any(Film.class))).thenReturn(film);

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

        Mockito.when(personMapper.personDTOToPerson(getPersonDtoTest(PERSON_ID))).thenReturn(getPerson());
        Mockito.when(filmMapper.filmToFilmDTO(any())).thenReturn(getFilmDTOAfterUpdate(FILM_ID));
        Mockito.when(filmRepo.saveAndFlush(any())).thenReturn(updatedRelations);
        Mockito.when(filmRepo.getOne(FILM_ID)).thenReturn(film);

        FilmDTO filmDTO = getSimpleTestFilm();

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

        Mockito.when(filmMapper.filmToFilmDTO(any())).thenReturn(getFilmDTOAfterUpdate(EDITED_FILM));
        Mockito.when(filmRepo.saveAndFlush(any())).thenReturn(getFilm(EDITED_FILM));
        Mockito.when(filmRepo.getOne(EDITED_FILM)).thenReturn(getFilm(EDITED_FILM));
        Mockito.when(personMapper.personDTOToPerson(getPersonDtoTest(ADDITIONAL_PERSON))).thenReturn(getSecondPerson());

        FilmDTO filmDTO = getSimpleTestFilm();
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

        Mockito.when(filmRepo.saveAndFlush(any())).thenReturn(film);
        Mockito.when(filmRepo.getOne(FILM_ID)).thenReturn(getFilm(FILM_ID));
        Mockito.when(filmMapper.filmDTOToFilm(any())).thenReturn(getFilm(FILM_ID));

        FilmDTO filmDTO = getSimpleTestFilm();

        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDTO personDtoTest = getPersonDtoTest(PERSON_ID);
        personDtoTest.getFilmList().add(filmDTO);
        personDTOList.add(personDtoTest);
        PersonDTO personDtoTestSecond = getPersonDtoTest(ADDITIONAL_PERSON);
        personDtoTestSecond.getFilmList().add(filmDTO);
        personDTOList.add(personDtoTestSecond);

        filmDTO.setPeopleList(personDTOList);

        Mockito.when(personMapper.personDTOToPerson(personDtoTest)).thenReturn(firstPerson);
        Mockito.when(personMapper.personDTOToPerson(personDtoTestSecond)).thenReturn(secondPerson);

        Mockito.when(filmMapper.filmToFilmDTO(any())).thenReturn(filmDTO);

        FilmDTO filmDTO1 = filmService.saveFilm(filmDTO).get();

        Assert.assertEquals(filmDTO1.getPeopleList().get(0).getId(), PERSON_ID);
        Assert.assertEquals(filmDTO1.getPeopleList().get(1).getId(), ADDITIONAL_PERSON);
    }

    @Test
    public void deleteFilm() {
        Film film = getFilm(FILM_ID);

        Mockito.when(filmRepo.findById(any())).thenReturn(Optional.of(film));

        FilmDTO filmDTO = new FilmDTO();

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

    private Person getPerson() {
        Person person = new Person();
        person.setFirstName(PERSON_FIRST_NAME);
        person.setLastName(PERSON_LAST_NAME);
        person.setId(PERSON_ID);
        return person;
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
}
