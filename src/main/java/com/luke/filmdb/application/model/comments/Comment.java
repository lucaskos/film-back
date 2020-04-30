package com.luke.filmdb.application.model.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luke.filmdb.application.model.generic.DataModelObject;
import com.luke.filmdb.application.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment extends DataModelObject implements Serializable {
    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = true)
    private User owner;
}
