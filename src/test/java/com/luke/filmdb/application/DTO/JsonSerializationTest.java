package com.luke.filmdb.application.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.model.comments.PersonComment;
import com.luke.filmdb.application.model.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static com.luke.filmdb.commons.CommentsCommon.getFilmComment;
import static com.luke.filmdb.commons.CommentsCommon.getPersonComment;
import static com.luke.filmdb.commons.MapperCommons.getSimpleFilm;
import static com.luke.filmdb.commons.UserCommons.getUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonSerializationTest {

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        FilmComment filmComment = getFilmComment();
        User user = getUser();
        filmComment.setOwner(user);
        Film simpleFilm = getSimpleFilm();
        simpleFilm.setFilmComments(Collections.singletonList(filmComment));
        filmComment.setFilm(simpleFilm);
        PersonComment personComment = getPersonComment();
        personComment.setOwner(user);

        String filmCommentAsString = objectMapper.writeValueAsString(filmComment);
        Assert.assertNotNull(filmCommentAsString);
        System.out.println(filmCommentAsString);
    }

}
