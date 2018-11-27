package com.example.demo.application.repository;

import com.example.demo.application.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.firstName LIKE CONCAT('%', :name, '%') OR p.lastName LIKE CONCAT('%', :name, '%')")
    List<Person> autocompleteByFirstNameOrLastName(String name);
}
