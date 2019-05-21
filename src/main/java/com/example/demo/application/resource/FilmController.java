package com.example.demo.application.resource;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.services.FilmService;
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
public class FilmController {

    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity getAllFilms() {
        return new ResponseEntity(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getFilmById(@PathVariable Long id) {
        return new ResponseEntity(filmService.getFilmDetails(id), HttpStatus.OK);
    }

	@PutMapping("/{id}")
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
        return new ResponseEntity(filmService.getFilmsByTitle(title), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addNewFilm(@RequestBody FilmDTO filmDTO) {
        return new ResponseEntity(filmService.saveFilm(filmDTO), HttpStatus.OK);
    }
}
