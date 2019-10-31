package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.DTO.mapper.PersonMapper;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.repository.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonMapper personMapper;
    private final PersonRepo personRepo;

    public List<PersonDTO> getAllPeople() {
        List<PersonDTO> list = new ArrayList<>();
        personRepo.findAll().forEach(person -> {
            list.add(personMapper.personToPersonDTO(person));
        });

        return list;
    }

    public PersonDTO getPerson(Long id) {
        return personMapper.personToPersonDTO(personRepo.findById(id).get());
    }

    public List<PersonDTO> findByName(String name) {
        List<Person> byFirstNameOrLastName = personRepo.autocompleteByFirstNameOrLastName(name);
        List<PersonDTO> list = new ArrayList<>();
        byFirstNameOrLastName.forEach(person -> list.add(personMapper.personToPersonDTO(person)));
        return list;
    }


    public PersonDTO addNewPerson(PersonDTO personDTO) {
        Person person = personMapper.personDTOToPerson(personDTO);
        Person save = personRepo.save(person);
        return personMapper.personToPersonDTO(save);
    }

    public void deletePerson(Long id) {
        personRepo.delete(personRepo.findById(id).get());
    }
}
