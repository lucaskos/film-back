package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.RatingDTO;
import com.luke.filmdb.application.commands.ObjectType;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.ObjectRating;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.repositories.FilmRepo;
import com.luke.filmdb.application.repositories.PersonRepo;
import com.luke.filmdb.application.repositories.RatingRepo;
import com.luke.filmdb.application.resources.filter.UserNotFoundException;
import com.luke.filmdb.commons.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepo ratingRepo;
    private final FilmRepo filmRepo;
    private final PersonRepo personRepo;
    private final SecurityUtils securityUtils;
    private final UserService userService;


    public void addRating(RatingDTO ratingDTO) {
        User user = null;
        try {
            user = userService.getCurrentlyLoggedUser();
        } catch (UserNotFoundException e) {
            //todo wynieść wszędzie do zewnętrznej wspólnej metody
            e.printStackTrace();
        }

        switch (ratingDTO.getObjectType()) {
            case FILM:
                Film one = filmRepo.getOne(ratingDTO.getObjectId());
                addFilmRating(one, user);
                break;
            case PERSON:
                Person person = personRepo.getOne(ratingDTO.getObjectId());
                addPersonRating(person, user);
                break;
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
