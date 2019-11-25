package com.luke.filmdb.application.model.comments;

import com.luke.filmdb.application.model.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = true)
    private User owner;
}
