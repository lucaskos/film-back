package com.example.demo.application.repository;

import com.example.demo.application.model.comments.FilmComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmCommentsRepo extends JpaRepository<FilmComment, Long> {
}
