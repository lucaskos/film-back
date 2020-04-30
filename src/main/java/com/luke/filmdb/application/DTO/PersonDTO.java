package com.luke.filmdb.application.DTO;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate bornDate;
    private LocalDate diedDate;
    private List<FilmDTO> filmList = new ArrayList<>(0);
    private String roleType;
    private String biography;
    private String role;
    private LocalDate creationDate;
    private LocalDate modificationDate;
}
