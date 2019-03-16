package com.example.demo.application.resource;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.services.FilmServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    private FilmServices filmService;

    public FilmController(FilmServices filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value = "/list")
    public List<FilmDTO> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/film/{id}")
    public FilmDTO getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @GetMapping("/{title}")
    public List<FilmDTO> getFilmsByTitle(@PathVariable String title) {
        return filmService.getFilmsByTitle(title);
    }

    @PostMapping(value = "/add")
    public FilmDTO addNewFilm(@RequestBody FilmDTO filmDTO) {
        return filmService.updateFilm(filmDTO);
    }

    @PutMapping(value = "/{id}")
    public FilmDTO updateFilm(@RequestBody FilmDTO filmDTO) {
        return filmService.updateFilm(filmDTO);
    }

    @DeleteMapping()
    public void deleteFilm(@RequestBody FilmDTO filmDTO) {
        filmService.deleteFilm(filmDTO);
    }
}
