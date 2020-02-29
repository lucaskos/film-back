package com.luke.filmdb.application.services;

import com.luke.filmdb.FilmDatabaseApplication;
import com.luke.filmdb.application.DTO.RatingDTO;
import com.luke.filmdb.application.commands.ObjectType;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.repositories.FilmRepo;
import com.luke.filmdb.application.repositories.PersonRepo;
import com.luke.filmdb.application.repositories.RatingRepo;
import com.luke.filmdb.commons.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FilmDatabaseApplication.class)
public class RatingServiceTest {


    @Mock
    private FilmRepo filmRepo;

    @Mock
    private PersonRepo personRepo;

    @Mock
    private RatingRepo ratingRepo;

    @Mock
    private SecurityUtils securityUtils;

    @Mock
    private UserService userService;

    @InjectMocks
    private RatingService ratingService;


    @Test
    @WithMockUser(username = "test")
    public void test() {

        //test
        Authentication authentication = Mockito.mock(Authentication.class);
// Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        //end test
//        Mockito.when(securityUtil.getCurrentlyLoggedUser()).thenReturn(getUser());
//        Mockito.when(userService.findOne(any())).thenReturn(User.getInstance());
        Mockito.when(filmRepo.getOne(any())).thenReturn(getFilm());

        ratingService.addRating(getRatingDTO());
    }

    private RatingDTO getRatingDTO() {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setObjectType(ObjectType.FILM);
        ratingDTO.setObjectId(1L);

        return ratingDTO;
    }

    private org.springframework.security.core.userdetails.User getUser() {
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User("test", "test", Collections.emptyList());

        return user;
    }

    private Film getFilm() {
        Film film = new Film();
        film.setId(1L);

        return film;
    }
}
