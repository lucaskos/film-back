package com.luke.filmdb.application.repository;

import com.luke.filmdb.application.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface FilmRepo extends JpaRepository<Film, Long> {

    Optional<List<Film>> findFilmsByTitleContainingIgnoreCase(String title);

    @Query("select f from Film f " +
            "left join fetch f.filmComments " +
//			"left join fetch f.filmRelations " +
            "where f.id = :film")
    Optional<Film> getFilmDetails(@Param("film") Long filmId);

}
