package com.example.demo.application.model;

import com.example.demo.application.model.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;

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
public class FilmComment extends Comment{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = true)
    private Film filmId;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private User userId;
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
}
