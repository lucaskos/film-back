package com.example.demo.application.DTO;

import com.example.demo.application.model.Person;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class FilmDTO {

    private Long filmId;
    private String title;
    private Integer year;
    private String description;
    private Date creationDate;
    private Date modificationDate;
    private List<PersonDTO>personList = new ArrayList<>();
}
