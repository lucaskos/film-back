package com.luke.filmdb.application.DTO.mapper;

import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person personDTOToPerson(PersonDTO filmDTO);
    PersonDTO personToPersonDTO(Person film);

}
