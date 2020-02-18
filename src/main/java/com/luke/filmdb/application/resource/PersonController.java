package com.luke.filmdb.application.resource;

import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin
@AllArgsConstructor
public class PersonController {

    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return new ResponseEntity<>(personService.getAllPeople(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable @NotNull Long id) {
        return new ResponseEntity<>(personService.getPerson(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<PersonDTO>> getPersonSearch(@PathVariable("name") String name) {
        return new ResponseEntity<>(this.personService.findByName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDTO) {
        return new ResponseEntity<>(this.personService.addNewPerson(personDTO), HttpStatus.CREATED);
    }
}
