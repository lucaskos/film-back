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
@Table(name = "SL_PERSON_ROLE")
public class PersonRole extends CacheAbstract{

    private Integer id;
    private String type;
    private String key;


    public PersonRole() {

    }

    public PersonRole(Integer id, String type, String key) {
        this.id = id;
        this.type = type;
        this.key = key;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SL_PERSON_ROLE_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer personRoleId) {
        this.id = personRoleId;
    }

    @Column(name = "SL_PERSON_ROLE_TYPE")
    public String getType() {
        return type;
    }

    public void setType(String personRoleType) {
        this.type = personRoleType;
    }

    @Column(name = "SL_PERSON_ROLE_KEY")
    public String getKey() {
        return key;
    }

    public void setKey(String personRoleKey) {
        this.key = personRoleKey;
    }

//    private Set<FilmRelations> filmRelations;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personRoleDictionary")
//    public Set<FilmRelations> getFilmRelations() {
//        return filmRelations;
//    }
//
//    public void setFilmRelations(Set<FilmRelations> filmRelations) {
//        this.filmRelations = filmRelations;
//    }
}
