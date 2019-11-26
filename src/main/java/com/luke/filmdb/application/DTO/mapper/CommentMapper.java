package com.luke.filmdb.application.DTO.mapper;

import com.luke.filmdb.application.DTO.CommentsDTO;
import com.luke.filmdb.application.model.comments.Comment;
import com.luke.filmdb.application.model.comments.FilmComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    FilmComment commentCommandToFilmCommentEntity(CommentsDTO commentsDTO);

    @Mapping(target = "user", source = "owner")
    CommentsDTO commentToCommentDTO(Comment comment);
}
