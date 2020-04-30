package com.luke.filmdb.application.resources;

import com.luke.filmdb.application.services.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;

import static com.luke.filmdb.application.resources.ControllerTest.convertToJsonString;
import static com.luke.filmdb.commons.MapperCommons.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = {PersonController.class})
@EnableWebMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAllPeople() throws Exception {
        when(personService.getAllPeople()).thenReturn(Collections.singletonList(getPersonDtoTest()));

        mockMvc.perform(get("/person"))
//                .andExpect(jsonPath("$[*]", hasProperty("firstName")))
                .andExpect(jsonPath("$[*].firstName").value(PERSON_FIRST_NAME))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getPersonByID() throws Exception {
        when(personService.getPerson(PERSON_ID)).thenReturn(getPersonDtoTest());

        mockMvc.perform(get("/person/id/{id}", PERSON_ID))
                .andExpect(jsonPath("$.firstName").value(PERSON_FIRST_NAME))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getPersonByName() throws Exception {
        when(personService.findByName(PERSON_FIRST_NAME)).thenReturn(Collections.singletonList(getPersonDtoTest()));

        mockMvc.perform(get("/person/name/{name}", PERSON_FIRST_NAME))
                .andExpect(jsonPath("$[*].firstName").value(PERSON_FIRST_NAME))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void addPerson() throws Exception {
        when(this.personService.addNewPerson(any())).thenReturn(getPersonDtoTest());

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .content(convertToJsonString(getPersonDtoTest()))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists());
    }

    @Test
    public void put() throws Exception {

    }
}
