package com.luke.filmdb.application.resource;

import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.services.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/film")
@CrossOrigin
@AllArgsConstructor
public class FilmController {

    private FilmService filmService;

    @GetMapping(value = "/list")
    public ResponseEntity getAllFilms() {
        return new ResponseEntity(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getFilmById(@PathVariable Long id) {
        return new ResponseEntity(filmService.getFilmDetails(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity updateFilm(@RequestBody FilmDTO filmDTO) {
        return new ResponseEntity(filmService.saveFilm(filmDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity getFilmsByTitle(@PathVariable String title) {
        return new ResponseEntity(filmService.getFilmDTOByTitle(title), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<FilmDTO> addNewFilm(@RequestBody FilmDTO filmDTO) {

        return filmService.saveFilm(filmDTO).map(film -> ResponseEntity.ok(film))
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

    }
}
