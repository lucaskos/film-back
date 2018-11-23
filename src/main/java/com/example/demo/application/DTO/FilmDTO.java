package com.example.demo.application.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class FilmDTO {

    private Long filmId;
    private String title;
    private Integer year;
    private String description;
    private Date creationDate;
    private Date modificationDate;
}
