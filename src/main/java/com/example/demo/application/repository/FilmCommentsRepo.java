package com.example.demo.application.repository;

import com.example.demo.application.model.comments.FilmComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmCommentsRepo extends JpaRepository<FilmComment, Long> {
}
