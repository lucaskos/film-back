package com.example.demo.application.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "FILM_COMMENTS")
@Data
public class FilmComments {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FILM_ID")
    private Integer filmId;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "DEPTH")
    private int depth;
    @Column(name = "PARENT_COMMENT_ID")
    private Long parentCommentId;
    @Column(name = "TEXT", nullable = false, columnDefinition = "TEXT")
    private String text;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "OWNER_ID")
    private Long userId;
}
