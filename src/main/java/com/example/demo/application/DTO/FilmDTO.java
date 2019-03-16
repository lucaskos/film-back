package com.example.demo.application.DTO;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
public class FilmDTO {

    private Long filmId;
    private String title;
    private Integer year;
    private String description;
    private LocalDate creationDate;
    private LocalDate modificationDate;
//    private MultiValueMap<String, PersonDTO> peopleList = new LinkedMultiValueMap<>();
    private List<PersonDTO> peopleList = new ArrayList<>();

    @Override
    public String toString() {
        return "FilmDTO{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
