package com.luke.filmdb.application.DTO.mapper;

import com.luke.filmdb.application.DTO.CommentDTO;
import com.luke.filmdb.application.model.comments.Comment;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.model.comments.PersonComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    FilmComment commentCommandToFilmCommentEntity(CommentDTO commentDTO);

    @Mapping(target = "user", source = "owner")
    CommentDTO commentToCommentDTO(Comment comment);

    PersonComment commentCommandToPersonCommandEntity(CommentDTO commentDto);
}
