package com.luke.filmdb.commons;

import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.model.Person;

public class PersonMapperCommons {

    public static PersonDTO getSimplePersonDTO() {
        PersonDTO personDTO = new PersonDTO();

        return personDTO;
    }

    public static Person getSimplePerson() {
        Person person = new Person();

        return person;
    }
}
