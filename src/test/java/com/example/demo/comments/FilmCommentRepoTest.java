package com.example.demo.comments;

import com.example.demo.DemoApplication;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.comments.FilmComment;
import com.example.demo.application.repository.FilmCommentsRepo;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.commons.CommentsCommon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class FilmCommentRepoTest extends CommentsCommon {

	@Autowired
	private FilmCommentsRepo filmCommentsRepo;
	@Autowired
	private FilmRepo filmRepo;

	@Test
	@Transactional
	public void addCommentToFilm() {
		Film film = filmRepo.getOne(1L);
		FilmComment filmComment = new FilmComment();

		filmComment.setParentCommentId(null);
		filmComment.setFilmId(film);
		filmComment.setText("PARENT_COMMENT_TEXT");
		filmComment.setTitle("PARENT_COMMENT_TITLE");

		FilmComment save = filmCommentsRepo.save(filmComment);

		Assert.assertNotNull(save);
		Assert.assertEquals(save.getSubComments(), new HashSet<>());
		Assert.assertNotNull(save.getId());

		//first sub comment of freshly added filmComment
		FilmComment newFilmComment = new FilmComment();
		newFilmComment.setParentCommentId(save);
		newFilmComment.setText("TEXT");
		newFilmComment.setFilmId(film);
		newFilmComment.setId(null);
		FilmComment save1 = filmCommentsRepo.save(newFilmComment);
		save.getSubComments().add(save1);

		filmCommentsRepo.save(save);

		List<FilmComment> filmComments = filmCommentsRepo.findDetails(save.getId()).get();

		Assert.assertNotNull(filmComments.get(0).getSubComments().iterator().next().getId());

	}
}
