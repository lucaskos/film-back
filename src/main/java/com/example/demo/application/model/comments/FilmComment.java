package com.example.demo.application.model.comments;

import com.example.demo.application.model.Film;
import com.example.demo.application.model.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "FILM_COMMENTS")
@Data
public class FilmComment {
	@ManyToOne
	@JoinColumn(name = "film_id", nullable = true)
	private Film filmId;
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = true)
	private User userId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "depth")
	private int depth;
	@Column(name = "text", nullable = false, columnDefinition = "text")
	private String text;
	@Column(name = "title", nullable = false)
	private String title;

	public FilmComment() {
	}
//
//	@Override
//	public String toString() {
//		return "FilmComment{" +
//				"commentId=" + super.getId() +
//				", title=" + super.getTitle() +
//				", text=" + super.getText() +
//				", deepth=" + super.getDepth() +
//				", creationDate" + super.getCreatedDate() +
//				'}';
//	}

	@Override
	public String toString() {
		return "FilmComment{" +
				"id=" + id +
				", createdDate=" + createdDate +
				", depth=" + depth +
				", text='" + text + '\'' +
				", title='" + title + '\'' +
				'}';
	}
}
