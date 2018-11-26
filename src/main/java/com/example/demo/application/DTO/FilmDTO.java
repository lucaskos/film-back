package com.example.demo.application.DTO;

import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.time.LocalDate;



@Data
public class FilmDTO {

    private Long filmId;
    private String title;
    private Integer year;
    private String description;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private MultiValueMap<String, PersonDTO> personList = new LinkedMultiValueMap<>();
}
