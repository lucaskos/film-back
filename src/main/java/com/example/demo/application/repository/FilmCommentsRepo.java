package com.example.demo.application.repository;

import com.example.demo.application.model.Film;
import com.example.demo.application.model.comments.FilmComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FilmCommentsRepo extends JpaRepository<FilmComment, Long> {

	@Query("select fc from FilmComment fc " +
			"where fc.id = :id")
	Optional<FilmComment> findCommentDetailsById(@Param("id") Long id);

	Optional<List<FilmComment>> findByFilmIdAndParentCommentIdIsNull(@Param("film") Film filmId);
}
