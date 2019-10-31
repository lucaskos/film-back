package com.luke.filmdb.application.DTO;


import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date bornDate;
    private Date diedDate;
    private List<FilmDTO> filmList = new ArrayList<>(0);
    private String roleType;
    private String biography;
    private String role;
    private Date creationDate;
    private Date modificationDate;
}
