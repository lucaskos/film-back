package com.luke.filmdb.application.model;


import com.luke.filmdb.application.model.generic.DataModelObject;
import com.luke.filmdb.application.model.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OBJECT_RATING")
@Data
public class ObjectRating extends DataModelObject {

    @Column(name = "rating")
    private int rating;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    /**
     * pozbycie sie relacji do obiektu. Ocena ogolna zapisywana na obiektcie i tam trzymana.
     * Tutaj dane są widoczne jedynie dla uzytkownika.
     */
    @Column(name = "object_typ")
    private String objectType;
    @Column(name = "object_id")
    private Long objectId;

}
