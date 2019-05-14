package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmComments;
import com.example.demo.application.model.FilmRelations;
import com.example.demo.application.model.Person;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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
    Film filmDTOToFilm(FilmDTO filmDTO);


    @AfterMapping
    default Film commentsDtoToFilmComments(@MappingTarget Film film, FilmDTO filmDTO) {
        List<FilmComments> commentsDTOS = new ArrayList<>();
        filmDTO.getFilmCommentsList().forEach(filmComment -> commentsDTOS.add(commentToFilmCommentDTO(filmComment)));
        film.getFilmComments().addAll(commentsDTOS);
        film.getFilmComments().forEach(filmComment -> filmComment.setFilmId(film));
        return film;
    }

    @AfterMapping
    default FilmDTO commentsToFilmDtoComments(@MappingTarget FilmDTO filmDTO, Film film) {
        List<CommentsDTO> commentsDTOS = new ArrayList<>();
        film.getFilmComments().forEach(filmComment -> commentsDTOS.add(filmToFilmDtoComments(filmComment)));
        filmDTO.getFilmCommentsList().addAll(commentsDTOS);
        return filmDTO;
    }

    PersonDTO personToPersonDTO(Person person);

    FilmComments commentToFilmCommentDTO(CommentsDTO commentsDTO);

    CommentsDTO filmToFilmDtoComments(FilmComments filmComments);

//    @Named("peopleToFilmRelations")
//    default List<FilmRelations> peopleToFilmRelation(FilmDTO film) {
//        for(PersonDTO person: film.getPeopleList()) {
//            FilmRelations filmRelations = new FilmRelations();
//            filmRelations.setRole(person.getRole());
//            filmRelations.setPerson();
//        }
//    }
}
