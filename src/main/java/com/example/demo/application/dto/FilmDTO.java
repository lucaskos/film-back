package com.example.demo.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class FilmDTO {

    private Integer filmId;
    private String title;
    private Integer year;
    private String description;
    private Date creationDate;
    private Date modificationDate;
}
