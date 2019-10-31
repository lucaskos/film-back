package com.luke.filmdb.application.repository;

import com.luke.filmdb.application.model.ObjectRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepo extends JpaRepository<ObjectRating, Long> {
}
