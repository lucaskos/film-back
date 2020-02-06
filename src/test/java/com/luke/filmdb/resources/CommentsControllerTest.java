package com.luke.filmdb.resources;

import com.luke.filmdb.application.resource.CommentsController;
import com.luke.filmdb.application.services.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;

import static com.luke.filmdb.commons.CommentsCommon.getFilmComment;
import static com.luke.filmdb.commons.MapperCommons.FILM_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {CommentsController.class})
@EnableWebMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void test() throws Exception {
        when(commentService.findComment(any())).thenReturn(Collections.singletonList(getFilmComment()));

        mockMvc.perform(get("/comment/{id}", FILM_ID))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
