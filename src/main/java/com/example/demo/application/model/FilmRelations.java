package com.example.demo.application.model;

import com.example.demo.application.model.dictionaries.PersonRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Luke on 29.10.2017.
 */
@Entity
@Table(name = "FILM_RELATION")
@Getter
@Setter
public class FilmRelations {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role")
    private String role;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "film_id", nullable = true)
//    @Transient\
    private Film film;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "person_id", nullable = true)
//    @Transientd
    private Person person;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_role_id")
    private PersonRole personRoleDictionary;

    @Override
    public String toString() {
        return new String();
    }
}
