package com.luke.filmdb.application.DTO.mapper;

import com.luke.filmdb.application.DTO.CommentsDTO;
import com.luke.filmdb.application.model.comments.Comment;
import com.luke.filmdb.application.model.comments.FilmComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    FilmComment commentCommandToFilmCommentEntity(CommentsDTO commentsDTO);

    CommentsDTO commentToCommentDTO(Comment comment);
}
