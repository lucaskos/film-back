package com.example.demo.application.repository;

import com.example.demo.application.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FilmRepo extends JpaRepository<Film, Long> {
}
