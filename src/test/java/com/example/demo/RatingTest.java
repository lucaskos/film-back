package com.example.demo;

import com.example.demo.application.DTO.RatingDTO;
import com.example.demo.application.DTO.mapper.CommentMapper;
import com.example.demo.application.commands.ObjectType;
import com.example.demo.application.model.user.User;
import com.example.demo.application.repository.FilmCommentsRepo;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.repository.PersonRepo;
import com.example.demo.application.repository.RatingRepo;
import com.example.demo.application.services.CommentService;
import com.example.demo.application.services.RatingService;
import com.example.demo.application.services.UserService;
import com.example.demo.commons.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class RatingTest {


    @Mock
    private FilmRepo filmRepo;

    @Mock
    private PersonRepo personRepo;

    @Mock
    private RatingRepo ratingRepo;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private UserService userService;

    @InjectMocks
    private RatingService ratingService;


    @Test
    @WithMockUser(username = "test")
    public void test() {
        Mockito.when(securityUtil.getCurrentlyLoggedUser()).thenReturn(getUser());
        Mockito.when(userService.findOne(Mockito.any())).thenReturn(new User());

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
}
