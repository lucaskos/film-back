package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.DTO.mapper.EntityMapper;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.repository.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {
    private final EntityMapper entityMapper;
    private final PersonRepo personRepo;

    public List<PersonDTO> getAllPeople() {
        List<PersonDTO> list = new ArrayList<>();
        personRepo.findAll().forEach(person -> {
            list.add(entityMapper.personToPersonDTO(person));
        });

        return list;
    }

    public PersonDTO getPerson(Long id) {
        return entityMapper.personToPersonDTO(personRepo.findById(id).get());
    }

    public List<PersonDTO> findByName(String name) {
        List<Person> byFirstNameOrLastName = personRepo.autocompleteByFirstNameOrLastName(name);
        List<PersonDTO> list = new ArrayList<>();
        byFirstNameOrLastName.forEach(person -> list.add(entityMapper.personToPersonDTO(person)));
        return list;
    }


    public PersonDTO addNewPerson(PersonDTO personDTO) {
        Person person = entityMapper.personDTOToPerson(personDTO);
        Person save = personRepo.save(person);
        return entityMapper.personToPersonDTO(save);
    }

    public void deletePerson(Long id) {
        personRepo.delete(personRepo.findById(id).get());
    }
}
