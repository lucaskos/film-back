package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmComments;
import com.example.demo.application.model.FilmRelations;
import com.example.demo.application.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface FilmMapper {

    @Mapping(source = "film", target = "peopleList", qualifiedByName = "peopleRolesMap")
    @Mapping(target = "filmId", source = "id")
    FilmDTO filmToFilmDTO(Film film);

    @Named("peopleRolesMap")
    default List<PersonDTO> personToPersonDTO(Film film) {
        Set<FilmRelations> filmRelations = film.getFilmRelations();

        MultiValueMap<String, PersonDTO> people = new LinkedMultiValueMap<>();
        List<PersonDTO> peopleList = new ArrayList<>();
        filmRelations.stream().forEach(filmRelations1 -> {
                    PersonDTO personDTO = personToPersonDTO(filmRelations1.getPerson());
                    personDTO.setRole(filmRelations1.getRole());
                    peopleList.add(personDTO);
                }
        );

        return peopleList;
    }

    @Mapping(target = "id", source = "filmId")
//    @Mapping(target = "filmRelations", source = "peopleList", qualifiedByName = "peopleToFilmRelations")
    @Mapping(source = "filmDTO.filmCommentsList", target = "filmComments", qualifiedByName = "filmCommentsMap")
    Film filmDTOToFilm(FilmDTO filmDTO);

    @Named("filmCommentsMap")
    default List<FilmComments> commentsDtoToFilmComments(FilmDTO filmDTO) {

        List<FilmComments> commentsDTOS = new ArrayList<>();
        return commentsDTOS;

    }

    PersonDTO personToPersonDTO(Person person);

//    @Named("peopleToFilmRelations")
//    default List<FilmRelations> peopleToFilmRelation(FilmDTO film) {
//        for(PersonDTO person: film.getPeopleList()) {
//            FilmRelations filmRelations = new FilmRelations();
//            filmRelations.setRole(person.getRole());
//            filmRelations.setPerson();
//        }
//    }
}
