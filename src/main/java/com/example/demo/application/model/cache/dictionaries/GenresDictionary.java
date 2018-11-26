package com.example.demo.application.model.cache.dictionaries;

import lombok.Data;

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
@Table(name = "SL_GENRES")
@Data
public class GenresDictionary {

    @Id
    @Column(name = "SL_GENRES_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "SL_GENRES_TYPE")
    private String type;

    @Column(name = "SL_GENRES_KEY")
    private String key;
}
