package com.example.demo.film;

import com.example.demo.DemoApplication;
import com.example.demo.application.model.Film;
import com.example.demo.application.repository.FilmRepo;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class FilmRepoTest extends FilmMapperTest {

	@Autowired
	private FilmRepo filmRepo;

	@Test
	@Transactional
	public void getSingleFilmThenOk() {
		Film one = filmRepo.getOne(Long.valueOf(1));

		Assert.assertEquals(Long.valueOf(1), one.getId());
		Assert.assertNotNull(one.getDescription());
	}

	@Test
	@Transactional
	public void getFilmDetails() {
		Film film = filmRepo.getFilmDetails(1L).get();
		Assert.assertNotNull(film.getFilmComments().get(0).getId());
		Assert.assertNotNull(film.getFilmRelations().iterator().next().getId());
	}

	@Test(expected = LazyInitializationException.class)
	public void getSingleFilmWithComments_lazyException() {
		Film film = filmRepo.getOne(1L);
		Assert.assertNotNull(film.getFilmComments().get(0).getId());
	}

	@Test(expected = LazyInitializationException.class)
	public void getSingleFilmWithFilmRelation_lazyException() {
		Film simpleFilm = getSimpleFilm();

		Film compareFilm = filmRepo.save(simpleFilm);

		Assert.assertEquals(simpleFilm.getTitle(), compareFilm.getTitle());
		Assert.assertNotNull(compareFilm.getId());

		Film film = filmRepo.getOne(compareFilm.getId());

		Assert.assertNotNull(film.getFilmRelations().iterator().next().getId());
	}

	@Test(expected = NoSuchElementException.class)
	public void getSingleFilmAndDelete() {
		Film film = getSimpleFilm();

		Film compareFilm = filmRepo.save(film);

		Film afterSaveFilm = filmRepo.getFilmDetails(compareFilm.getId()).get();
		filmRepo.delete(afterSaveFilm);
		filmRepo.getFilmDetails(afterSaveFilm.getId()).get();
	}

	@Test
	public void getSingleFilmWithComments() {
		Film film = filmRepo.getFilmDetails(1L).get();
		Assert.assertNotNull(film.getFilmComments());
	}

	@Test
	//todo add foreign constraint on parentCommentId
	public void deleteSingleFilmWithComments_persistComments() {
		Film film = filmRepo.getFilmDetails(1L).get();
		filmRepo.delete(film);
	}

}
