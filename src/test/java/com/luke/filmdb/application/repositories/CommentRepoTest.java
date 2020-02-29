package com.luke.filmdb.application.repositories;

import com.luke.filmdb.FilmDatabaseApplication;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.commons.CommentsCommon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FilmDatabaseApplication.class)
@TestPropertySource(properties = "spring.profiles.active=dev")
public class CommentRepoTest extends CommentsCommon {

    @Autowired
    private FilmCommentsRepo filmCommentsRepo;
    @Autowired
    private FilmRepo filmRepo;
    @Autowired
    private UserRepository userRepository;

    private Long filmIdToDelete;
    /** CommentId to check when we delete film */
    private Long commentIdToCheckAfterFilmDeletion;

    @Before
    public void init() {
        Film film = filmRepo.getOne(2L);
        filmIdToDelete = film.getId();

        FilmComment filmComment = new FilmComment();

        filmComment.setFilm(film);
        filmComment.setText("PARENT_COMMENT_TEXT");
        filmComment.setTitle("PARENT_COMMENT_TITLE");

        film.getFilmComments().add(filmComment);
        Film save = filmRepo.save(film);
        List<FilmComment> filmComments = save.getFilmComments();

        FilmComment singleComment = filmComments.get(0);

        commentIdToCheckAfterFilmDeletion = singleComment.getId();
    }

    @Test
    @Transactional
    public void addCommentToFilm() {
        Film film = filmRepo.getOne(1L);
        FilmComment filmComment = new FilmComment();

        filmComment.setFilm(film);
        filmComment.setText("PARENT_COMMENT_TEXT");
        filmComment.setTitle("PARENT_COMMENT_TITLE");
        filmComment.setOwner(userRepository.getOne(1L));

        FilmComment save = filmCommentsRepo.save(filmComment);

        Assert.assertNotNull(save);
        Assert.assertNotNull(save.getId());
        Assert.assertNotNull(save.getOwner());

        //first sub comment of freshly added filmComment
        FilmComment newFilmComment = new FilmComment();
        newFilmComment.setText("TEXT");
        newFilmComment.setFilm(film);
        newFilmComment.setId(null);
        FilmComment save1 = filmCommentsRepo.save(newFilmComment);

        filmCommentsRepo.save(save);

        FilmComment filmComments = filmCommentsRepo.findCommentDetailsById(save.getId()).get();
    }

    @Test(expected = ObjectRetrievalFailureException.class)
    @Transactional
    public void deleteAllFilmComments() {
        Film one = filmRepo.getOne(filmIdToDelete);
        filmRepo.delete(one);

        filmCommentsRepo.getOne(commentIdToCheckAfterFilmDeletion);
    }
}
