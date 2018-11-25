package com.example.demo.application.resource;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.mapper.FilmMapper;
import com.example.demo.application.services.FilmServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    private FilmServices filmService;
    private FilmMapper filmMapper;

    public FilmController(FilmServices filmService, FilmMapper filmMapper) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
    }

    @GetMapping(value = "/list")
    public List<FilmDTO> getFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public FilmDTO getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PostMapping(value = "/add")
    public FilmDTO addNewFilm(@RequestBody FilmDTO filmDTO) {
        return filmService.updateFilm(filmMapper.filmDTOToFilm(filmDTO));
    }
}
