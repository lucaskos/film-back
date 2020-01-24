package resources;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.resource.UserController;
import com.luke.filmdb.application.services.UserService;
import com.luke.filmdb.security.jwt.TokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;

import static com.luke.filmdb.commons.UserCommons.EMAIL_ADDRESS;
import static com.luke.filmdb.commons.UserCommons.FIRST_NAME;
import static com.luke.filmdb.commons.UserCommons.ROLE_USER;
import static com.luke.filmdb.commons.UserCommons.USERNAME;
import static com.luke.filmdb.commons.UserCommons.getRegisterDTO;
import static com.luke.filmdb.commons.UserCommons.getUser;
import static com.luke.filmdb.commons.UserCommons.getUserDTO;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = {UserController.class})
@EnableWebMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest extends ControllerTest{
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenProvider jwtTokenUtil;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAllUsers() throws Exception{
        when(userService.findAll()).thenReturn(Collections.singletonList(getUserDTO()));

        mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].firstName").value(FIRST_NAME));
    }
    @Test
    public void getEmail() throws Exception{
        mockMvc.perform(get("/user/register/checkEmail/{email}", EMAIL_ADDRESS))
                .andDo(print())
                .andExpect(jsonPath("$").value("true"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserRoles() throws Exception{
        when(userService.findLoggedUserRoles()).thenReturn(Collections.singletonList(ROLE_USER));

        mockMvc.perform(get("/user/userRoles"))
                .andDo(print())
                .andExpect(jsonPath("$").value(ROLE_USER))
                .andExpect(status().isOk());
    }

    @Test
    public void registerTest() throws Exception {
        when(userService.saveNewUser(any(RegisterDTO.class))).thenReturn(getUser());

        mockMvc.perform(post("/user/register")
                .content(convertToJsonString(getRegisterDTO()))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(USERNAME));

    }
    @Test
    public void updateUserTest() throws Exception {
        when(userService.update(any(UserDTO.class))).thenReturn(getUserDTO());

        mockMvc.perform(put("/user/update")
                .content(convertToJsonString(getUserDTO()))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME));
    }

    public void getAuthToken() throws Exception {
        mockMvc.perform(post("/user/signin"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasProperty("login")));
    }
}
