package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmRelations;
import com.example.demo.application.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(source = "film", target = "personList", qualifiedByName = "peopleRolesMap")
    FilmDTO filmToFilmDTO(Film film);

    @Named("peopleRolesMap")
    default List<PersonDTO> personToPersonDTO(Film film) {
        Set<FilmRelations> filmRelations = film.getFilmRelations();

//        MultiMap<String, PersonDTO> people = new MultiMap<>();
        List<PersonDTO> list = new ArrayList();
        filmRelations.stream().forEach(filmRelations1 -> {
//                    people.add(filmRelations1.getRole(), personToPersonDTO(filmRelations1.getPerson()));
                    if (filmRelations1.getPersonRoleDictionary() != null) {
                        list.add(personToPersonDTO(filmRelations1.getPerson()));
                    }
                }
        );

        return list;
    }

    Film filmDTOToFilm(FilmDTO filmDTO);

    PersonDTO personToPersonDTO(Person person);
}
