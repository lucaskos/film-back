package com.luke.filmdb.application.DTO.mapper;

import com.luke.filmdb.application.DTO.CommentDTO;
import com.luke.filmdb.application.model.comments.Comment;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.model.comments.PersonComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    FilmComment commentCommandToFilmCommentEntity(CommentDTO commentDTO);

    @Mappings({
            @Mapping(target = "entityId", ignore = true),
            @Mapping(target = "user", source = "owner")
    })
    CommentDTO commentToCommentDTO(Comment comment);

    PersonComment commentCommandToPersonCommandEntity(CommentDTO commentDto);
}
