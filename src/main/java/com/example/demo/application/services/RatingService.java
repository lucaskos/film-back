package com.example.demo.application.services;

import com.example.demo.application.DTO.RatingDTO;
import com.example.demo.application.commands.ObjectType;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.ObjectRating;
import com.example.demo.application.model.Person;
import com.example.demo.application.model.user.User;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.repository.PersonRepo;
import com.example.demo.application.repository.RatingRepo;
import com.example.demo.commons.SecurityUtil;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private RatingRepo ratingRepo;
    private FilmRepo filmRepo;
    private PersonRepo personRepo;
    private SecurityUtil securityUtil;

    public RatingService(RatingRepo ratingRepo, FilmRepo filmRepo, PersonRepo personRepo, SecurityUtil securityUtil) {
        this.ratingRepo = ratingRepo;
        this.filmRepo = filmRepo;
        this.personRepo = personRepo;
        this.securityUtil = securityUtil;
    }

    public void addRating(RatingDTO ratingDTO) {
        org.springframework.security.core.userdetails.User currentlyLoggedUser = securityUtil.getCurrentlyLoggedUser();
        switch (ratingDTO.getObjectType()) {
            case FILM:
                Film one = filmRepo.getOne(ratingDTO.getObjectId());
                addFilmRating(one);
            case PERSON:
                return;
        }
    }

    private ObjectRating addFilmRating(Film film, User user) {
        ObjectRating rating = setRatingObject(ObjectType.PERSON.toString(), film.getId());
        rating.setUser(user);

        return ratingRepo.save(rating);
    }

    private ObjectRating addPersonRating(Person person, User user) {
        ObjectRating rating = setRatingObject(ObjectType.PERSON.toString(), person.getId());
        rating.setUser(user);

        return ratingRepo.save(rating);
    }

    private ObjectRating setRatingObject(String objectType, Long objectId) {
        ObjectRating rating = new ObjectRating();

        rating.setObjectType(objectType);
        rating.setObjectId(objectId);
        return rating;
    }
}
