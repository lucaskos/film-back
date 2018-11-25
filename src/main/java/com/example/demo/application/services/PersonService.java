package com.example.demo.application.services;

import com.example.demo.application.DTO.PersonDTO;
import com.example.demo.application.DTO.mapper.PersonMapper;
import com.example.demo.application.model.Person;
import com.example.demo.application.repository.PersonRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private PersonMapper personMapper;
    private PersonRepo personRepo;

    public PersonService(PersonMapper personMapper, PersonRepo personRepo) {
        this.personMapper = personMapper;
        this.personRepo = personRepo;
    }

    public PersonDTO getPerson(Long id){
       return personMapper.personToPersonDTO(personRepo.findById(id).get());
    }

    public List<PersonDTO> findByName(String name) {
        List<Person> byFirstNameOrLastName = personRepo.findByFirstNameOrLastName(name);
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
