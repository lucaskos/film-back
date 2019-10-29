package com.example.demo.application.model.comments;

import com.example.demo.application.model.Person;
import com.example.demo.application.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PERSON_COMMENT")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonComment extends Comment {

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_comment")
    private PersonComment parentComment;
    @JsonIgnore
    @OneToMany(mappedBy = "parentComment")
    private Set<PersonComment> subComments = new HashSet<>();

    @Override
    public String toString() {
        return "PersonComment{" +
                "person=" + person +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonComment)) return false;
        if (!super.equals(o)) return false;
        PersonComment that = (PersonComment) o;
        return Objects.equals(getPerson(), that.getPerson()) &&
                Objects.equals(getParentComment(), that.getParentComment()) &&
                Objects.equals(getSubComments(), that.getSubComments());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        return result;
    }
}
