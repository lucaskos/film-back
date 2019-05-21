package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.CommentsDTO;
import com.example.demo.application.model.FilmComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	FilmComment commentCommandToFilmCommentEntity(CommentsDTO commentsDTO);
}
