package com.example.demo.application.repository;

import com.example.demo.application.model.ObjectRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepo extends JpaRepository<ObjectRating, Long> {
}
