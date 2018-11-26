package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmRelations;
import com.example.demo.application.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(source = "film", target = "personList", qualifiedByName = "peopleRolesMap")
    @Mapping(target = "filmId", source = "id")
    FilmDTO filmToFilmDTO(Film film);

    @Named("peopleRolesMap")
    default MultiValueMap<String, PersonDTO> personToPersonDTO(Film film) {
        Set<FilmRelations> filmRelations = film.getFilmRelations();

        MultiValueMap<String, PersonDTO> people = new LinkedMultiValueMap<>();
        filmRelations.stream().forEach(filmRelations1 -> {
            PersonDTO personDTO = personToPersonDTO(filmRelations1.getPerson());
            personDTO.setRole(filmRelations1.getRole());
            people.add(filmRelations1.getPersonRoleDictionary().getType(), personDTO);
                }
        );

        return people;
    }

    @Mapping(target = "id", source = "filmId")
    Film filmDTOToFilm(FilmDTO filmDTO);

    PersonDTO personToPersonDTO(Person person);
}
