package com.example.demo.application.dto.mapper;

import com.example.demo.application.dto.FilmDTO;
import com.example.demo.model.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    FilmDTO filmToFilmDTO(Film film);

    FilmDTO filmDTOToFilm(FilmDTO filmDTO);
}
