package com.luke.filmdb.application.DTO;


import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
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
