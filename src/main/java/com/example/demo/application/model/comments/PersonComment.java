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
import java.util.Set;

@Entity
@Table(name = "PERSON_COMMENTS")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonComment extends Comment {

	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User userId;
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_comment_id")
	private PersonComment parentCommentId;
	@JsonIgnore
	@OneToMany(mappedBy = "parentCommentId")
	private Set<PersonComment> subComments = new HashSet<>();

	@Override
	public String toString() {
		return "PersonComment{" +
				"person=" + person +
				", userId=" + userId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PersonComment)) return false;
		if (!super.equals(o)) return false;

		PersonComment that = (PersonComment) o;

		if (getPerson() != null ? !getPerson().equals(that.getPerson()) : that.getPerson() != null) return false;
		return getUserId() != null ? getUserId().equals(that.getUserId()) : that.getUserId() == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
		result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
		return result;
	}
}
