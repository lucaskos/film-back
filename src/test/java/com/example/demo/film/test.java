//package com.example.demo.film;
//
//import com.example.demo.application.model.Film;
//import com.example.demo.application.model.comments.FilmComment;
//import com.example.demo.application.model.FilmRelations;
//import com.example.demo.application.repository.FilmRepo;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Set;
//
//public class test {
//	@Autowired
//	private FilmRepo filmRepo;
//
//	@Test
//	public void getSingleFilmThenOk() {
//		Film one = filmRepo.getOne(Long.valueOf(1));
//
//		Assert.assertEquals(Long.valueOf(1), one.getId());
//	}
//
//	@Test
//	public void getAllFilms() {
//		List<Film> filmList = filmRepo.findAll();
//		List<FilmComment> filmComments = filmList.get(0).getFilmComments();
//		Set<FilmRelations> filmRelations = filmList.get(0).getFilmRelations();
//		Assert.assertTrue(filmList.size() > 0);
//	}
//}
