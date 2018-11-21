package com.example.demo.application.resource;

import com.example.demo.application.dto.FilmDTO;
import com.example.demo.application.dto.mapper.FilmMapper;
import com.example.demo.model.Film;
import com.example.demo.repository.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private FilmRepo filmRepo;

    @Autowired
    private FilmMapper filmMapper;

    @GetMapping(value = "/list")
    public FilmDTO getFilm() {
        Film film = filmRepo.findAll().get(0);
        film.setFilmRelations(null);
        return filmMapper.filmToFilmDTO(film);
    }

}
