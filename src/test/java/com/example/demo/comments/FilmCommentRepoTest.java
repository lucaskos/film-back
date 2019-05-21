package com.example.demo.comments;

import com.example.demo.DemoApplication;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.FilmComment;
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
		FilmComment filmComment = getFilmComment();

		filmComment.setFilmId(film);

		FilmComment save = filmCommentsRepo.save(filmComment);

		Assert.assertNotNull(save);

	}
}
