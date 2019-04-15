package com.example.demo.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "FILM_COMMENTS")
@Data
public class FilmComments {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
//    @Column(name = "film_id")
    private Film filmId;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "depth")
    private int depth;
    @Column(name = "parent_comment_id")
    private Long parentCommentId;
    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "owner_id")
    private Long userId;
}
