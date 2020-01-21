package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luke.filmdb.application.DTO.FilmDTO;
import com.luke.filmdb.application.resource.FilmController;
import com.luke.filmdb.application.resource.TestController;
import com.luke.filmdb.application.services.FilmService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;

import static com.luke.filmdb.commons.MapperCommons.FILM_ID;
import static com.luke.filmdb.commons.MapperCommons.FILM_TITLE;
import static com.luke.filmdb.commons.MapperCommons.getSimpleDTOFilm;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {FilmController.class, TestController.class})
@EnableWebMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAllFilms() throws Exception {
        when(filmService.getAllFilms()).thenReturn(Collections.singletonList(getSimpleDTOFilm()));

        mockMvc.perform(
                get("/film/list")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].title").value(FILM_TITLE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getOneFilmById() throws Exception {
        when(filmService.getFilmDetails(FILM_ID)).thenReturn(getSimpleDTOFilm());

        mockMvc.perform(get("/film/" + FILM_ID))
                .andExpect(jsonPath("$.title").value(FILM_TITLE))
                .andExpect(jsonPath("$.filmId").value(FILM_ID))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getFilmsByTitle() throws Exception {
        when(filmService.getFilmDTOByTitle(FILM_TITLE)).thenReturn(Collections.singletonList(getSimpleDTOFilm()));

        mockMvc.perform(get("/film/title/" + FILM_TITLE)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].title").value(FILM_TITLE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void putOneFilm() throws Exception {
        when(filmService.saveFilm(ArgumentMatchers.any(FilmDTO.class))).thenReturn(java.util.Optional.of(getSimpleDTOFilm()));

        mockMvc.perform(put("/film")
                .content(convertToJsonString(getSimpleDTOFilm()))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void postNewFilm() throws Exception {
        when(filmService.saveFilm(ArgumentMatchers.any(FilmDTO.class))).thenReturn(java.util.Optional.of(getSimpleDTOFilm()));

        mockMvc.perform(post("/film/add")
                .content(convertToJsonString(getSimpleDTOFilm()))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.filmId").exists());
    }

    private static String convertToJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
