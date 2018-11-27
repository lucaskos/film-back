package com.example.demo.application.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "PERSON_COMMENTS")
@Data
public class PersonComments{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "DEPTH")
    private Integer depth;
    @Column(name = "PARENT_COMMENT_ID")
    private Integer parentCommentId;
    @Size(min = 10)
    @Column(name = "TEXT", nullable = false, columnDefinition = "TEXT")
    private String text;
    @Size(min = 5)
    @Column(name = "TITLE", nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;
}
