package com.example.demo.application.model;


import com.example.demo.application.model.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "inertRating",
                procedureName = "INSERT_RATING",
                resultClasses = {Rating.class},
                parameters = {
                        @StoredProcedureParameter(
                                name = "in_user_id",
                                type = Integer.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "in_film_id",
                                type = Integer.class,
                                mode = ParameterMode.IN),

                        @StoredProcedureParameter(
                                name = "in_person_id",
                                type = Integer.class,
                                mode = ParameterMode.IN
                        ),

                        @StoredProcedureParameter(
                                name = "in_rating",
                                type = Integer.class,
                                mode = ParameterMode.IN
                        )
                        ,
//                        @StoredProcedureParameter(
//                                name = "out_result",
//                                type = Integer.class,
//                                mode = ParameterMode.OUT
//                        )
                })
})
@Entity
@Table(name = "rating_films")
public class Rating {

    private Long ratingId;
    private int rating;
    private User userId;
    private Integer filmId;
    private Integer personId;

    public Rating() {

    }

    public Rating(int rating, User userId, int filmId) {
        this.rating = rating;
        this.userId = userId;
        this.filmId = filmId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    @JoinColumn(name = "film")
    public Integer getFilmId() {
        return filmId;
    }

    @Column(name = "rating")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    @Column(name = "person_id")
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "Rating [ratingId=" + ratingId + ", rating=" + rating + ", user=" + userId + ", film=" + filmId + "]";
    }

}
