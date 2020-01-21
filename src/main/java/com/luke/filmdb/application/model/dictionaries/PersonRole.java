package com.luke.filmdb.application.model.dictionaries;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Luke on 25.10.2017.
 */
@Entity
@Table(name = "SL_PERSON_ROLE")
@Getter
@Setter
public class PersonRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SL_PERSON_ROLE_TYPE")
    private String type;

    @Column(name = "SL_PERSON_ROLE_KEY")
    private String key;
}
