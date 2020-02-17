package com.luke.filmdb.application.model.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luke.filmdb.application.model.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "PERSON_COMMENT")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonComment extends Comment {

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;
//    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//    @JoinColumn(name = "parent_comment")
//    private PersonComment parentComment;
//    @JsonIgnore
//    @OneToMany(mappedBy = "parentComment")
//    private Set<PersonComment> subComments = new HashSet<>();


    public PersonComment() {
    }

    public PersonComment(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "PersonComment{" +
                "person=" + person +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PersonComment that = (PersonComment) o;

        return Objects.equals(getPerson().getFirstName(), that.getPerson().getLastName())
                && Objects.equals(getPerson().getLastName(), that.getPerson().getLastName())
                && Objects.equals(getPerson().getBornDate(), that.getPerson().getBornDate());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        return result;
    }
}
