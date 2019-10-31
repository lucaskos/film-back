package com.luke.filmdb.application.repository;

import com.luke.filmdb.application.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.firstName LIKE CONCAT('%', :name, '%') OR p.lastName LIKE CONCAT('%', :name, '%')")
    List<Person> autocompleteByFirstNameOrLastName(String name);

    @Query("select p from Person p where p.id = :personId")
    Optional<Person> getPersonDetails(@Param("personId") Long entityId);

}
