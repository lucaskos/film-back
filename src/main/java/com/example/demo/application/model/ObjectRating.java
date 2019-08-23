package com.example.demo.application.model;


import com.example.demo.application.model.generic.DataModelObject;
import com.example.demo.application.model.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
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
