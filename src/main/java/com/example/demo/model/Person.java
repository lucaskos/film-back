package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PERSON")
public class Person implements Serializable {

    private static final long serialVersionUID = 5650070241555490348L;
    private Integer id;
    private String firstName;
    private String lastName;
    private Date bornDate;
    private Date diedDate;
    private Date creationDate;
    private Date modificationDate;
    private Set<PersonComments> personComments;
    /**
     * biography of a person
     */
    private String bio;
    @JsonIgnore
    @OneToMany(targetEntity = FilmRelations.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType
            .ALL)
    private List<FilmRelations> filmRelations = new ArrayList<>(0);

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType
            .ALL)
    public List<FilmRelations> getFilmRelations() {
        return filmRelations;
    }

    public void setFilmRelations(List<FilmRelations> filmRelations) {
        this.filmRelations = filmRelations;
    }

    public Person() {
    }
    public Person(String firstName) {
        this.firstName = firstName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PERSON_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "BORN_DATE")
    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    @Column(name = "DIED_DATE")
    public Date getDiedDate() {
        return diedDate;
    }

    public void setDiedDate(Date diedDate) {
        this.diedDate = diedDate;
    }

    @Column(name = "BIOGRAPHY", columnDefinition = "TEXT")
    @Lob
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Column(name = "CREATION_DATE")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "MODIFICATION_DATE")
    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_PERSON_ID")
    public Set<PersonComments> getPersonComments() {
        return personComments;
    }

    public void setPersonComments(Set<PersonComments> personComments) {
        this.personComments = personComments;
    }

    public void addPersonComments(PersonComments comment) {
        if(this.personComments == null) {
            this.personComments = new LinkedHashSet<>();
            this.personComments.add(comment);
        } else {
            this.personComments.add(comment);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (id != other.id)
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        return true;
    }


}
