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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "film_id")
    private Integer filmId;
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
