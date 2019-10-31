package com.luke.filmdb.film;

import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.services.FilmService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmServiceSecurityTest {

    @Autowired
    private FilmService filmService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addFilmWithoutUser_AuthenticationException(){
        FilmDTO filmDTO = new FilmDTO();
        filmService.addFilm(filmDTO);
    }

    @WithMockUser(authorities = {"USER"})
    @Test(expected = AccessDeniedException.class)
    public void addFilmWithReadOnlyUser_AccessDeniedException() {
        FilmDTO filmDTO = new FilmDTO();
        filmService.addFilm(filmDTO);
    }

    @WithMockUser(authorities = {"EDITOR"})
    @Test
    public void addFilmWithMockUser_returnFilm() {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle("TEST_TITLE");
        filmService.addFilm(filmDTO);
    }

}
