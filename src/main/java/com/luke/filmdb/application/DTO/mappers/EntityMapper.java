package com.luke.filmdb.application.DTO.mappers;

import com.luke.filmdb.application.DTO.CommentDTO;
import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.commands.ObjectType;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.model.comments.FilmComment;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    Person personDTOToPerson(PersonDTO filmDTO);

    PersonDTO personToPersonDTO(Person person);

    //    @Mapping(source = "film", target = "peopleList", qualifiedByName = "peopleRolesMap")
    @Mapping(target = "filmId", source = "id")
    FilmDTO filmToFilmDTO(Film film);

//    @Named("peopleRolesMap")
//    default List<PersonDTO> personToPersonDTO(Film film) {
//        Set<FilmRelations> filmRelations = film.getFilmRelations();
//
//        List<PersonDTO> peopleList = new ArrayList<>();
//        filmRelations.stream().forEach(filmRelations1 -> {
//                    PersonDTO personDTO = personToPersonDTO(filmRelations1.getPerson());
//                    personDTO.setRole(filmRelations1.getRole());
//                    peopleList.add(personDTO);
//                }
//        );
//        return peopleList;
//    }

    @Mapping(target = "id", source = "filmId")
    @Mapping(source = "filmCommentsList", target = "filmComments", qualifiedByName = "commentToFilmCommentDTO")
    Film filmDTOToFilm(FilmDTO filmDTO);

    @Named("commentToFilmCommentDTO")
    default FilmComment commentToFilmCommentDTO(CommentDTO commentDTO) {
        FilmComment filmComment = new FilmComment();

        filmComment.setText(commentDTO.getText());
        filmComment.setTitle(commentDTO.getTitle());
        filmComment.setId(commentDTO.getId());

        return filmComment;
    }


    //	@AfterMapping
//	default Film commentsDtoToFilmComments(@MappingTarget Film film, FilmDTO filmDTO) {
//		List<FilmComment> commentsDTOS = new ArrayList<>();
//
//		filmDTO.getFilmCommentsList().forEach(filmComment -> commentsDTOS.add(commentToFilmCommentDTO(filmComment)));
//		film.getFilmComments().addAll(commentsDTOS);
//
//		film.getFilmComments().forEach(filmComment -> filmComment.setFilm(film));
//		return film;
//	}
//
    @AfterMapping
    default FilmDTO commentsToFilmDtoComments(@MappingTarget FilmDTO filmDTO, Film film) {
        List<CommentDTO> commentsDTOS = new ArrayList<>();

        film.getFilmComments().forEach(filmComment -> {
            commentsDTOS.add(filmToFilmDtoComments(filmComment));
        });
        filmDTO.getFilmCommentsList().addAll(commentsDTOS);

        filmDTO.getFilmCommentsList().forEach(commentsDTO -> {
            commentsDTO.setEntityId(filmDTO.getFilmId());
            commentsDTO.setEntityType(ObjectType.FILM.name());
        });

        return filmDTO;
    }

    CommentDTO filmToFilmDtoComments(FilmComment filmComment);

//    @Named("peopleToFilmRelations")
//    default List<FilmRelations> peopleToFilmRelation(FilmDTO film) {
//        for(PersonDTO person: film.getPeopleList()) {
//            FilmRelations filmRelations = new FilmRelations();
//            filmRelations.setRole(person.getRole());
//            filmRelations.setPerson();
//        }
//    }
}
