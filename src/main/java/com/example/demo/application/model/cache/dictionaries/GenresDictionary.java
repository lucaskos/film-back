package com.example.demo.application.model.cache.dictionaries;

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
public class GenresDictionary extends CacheAbstract {

    @Id
    @Column(name = "SL_GENRES_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "SL_GENRES_TYPE")
    private String type;

    @Column(name = "SL_GENRES_KEY")
    private String key;

    public GenresDictionary() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer genreId) {
        this.id = genreId;
    }

    public String getType() {
        return type;
    }

    public void setType(String genreName) {
        this.type = genreName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
