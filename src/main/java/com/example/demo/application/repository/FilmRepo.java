package com.example.demo.application.repository;

import com.example.demo.application.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface FilmRepo extends JpaRepository<Film, Long> {

    Film getByTitle(String title);

    @Query("select f from Film f where f.title LIKE CONCAT('%', :title, '%')")
    List<Film> autocompleteByTitle(String title);
}
