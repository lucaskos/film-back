package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.FilmDTO;
import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person personDTOToPerson(PersonDTO filmDTO);
    PersonDTO personToPersonDTO(Person film);

}
