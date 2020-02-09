package com.luke.filmdb.mapper;

import com.luke.filmdb.application.DTO.CommentDTO;
import com.luke.filmdb.application.DTO.mapper.CommentMapper;
import com.luke.filmdb.application.model.comments.FilmComment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.luke.filmdb.commons.CommentsCommon.getFilmComment;
import static com.luke.filmdb.commons.CommentsCommon.getFilmCommentDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void filmCommentToDto() {
        FilmComment filmComment = getFilmComment();

        CommentDTO commentDTO = commentMapper.commentToCommentDTO(filmComment);

        Assert.assertEquals(filmComment.getText(), commentDTO.getText());
        Assert.assertEquals(filmComment.getTitle(), commentDTO.getTitle());
        Assert.assertEquals(filmComment.getId(), commentDTO.getId());
        Assert.assertEquals(filmComment.getCreationDate(), commentDTO.getCreatedDate());
    }

    @Test
    public void commentCommandToPersonCommand() {
        CommentDTO filmCommand = getFilmCommentDTO();

        FilmComment filmComment = commentMapper.commentCommandToFilmCommentEntity(filmCommand);

        Assert.assertEquals(filmCommand.getText(), filmComment.getText());
        Assert.assertEquals(filmCommand.getTitle(), filmComment.getTitle());
    }

}
