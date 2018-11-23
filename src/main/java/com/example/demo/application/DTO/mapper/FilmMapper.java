package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.model.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    FilmDTO filmToFilmDTO(Film film);

    Film filmDTOToFilm(FilmDTO filmDTO);
}
