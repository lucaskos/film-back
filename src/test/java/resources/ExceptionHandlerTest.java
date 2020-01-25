package resources;

import com.luke.filmdb.application.resource.CustomExceptionHandler;
import com.luke.filmdb.application.resource.ErrorMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@ContextConfiguration(classes = {
        CustomExceptionHandler.class,
        ExceptionHandlerTest.RestProcessingExceptionThrowingController.class,
        ExceptionHandlerTest.TestConfiguration.class})
@EnableWebMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionHandlerTest {
    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Configuration
    // !!! this is very important - conf with this annotation
    //     must be included in @ContextConfiguration
    @EnableWebMvc
    public static class TestConfiguration {
    }


    @Controller
    @RequestMapping("/tests")
    public static class RestProcessingExceptionThrowingController {
        @RequestMapping(value = "/exception", method = GET)
        public @ResponseBody
        String find() throws ErrorMessage {
            throw new ErrorMessage(401,"global_error_test");
        }
    }


    @Test
    public void testHandleException() throws Exception {
        mockMvc.perform(get("/tests/exception"))
                .andDo(print())
                .andExpect(result -> result.getResponse().getContentAsString().contains("global_error_test"));
//                .andExpect(status().isBadRequest()); todo request type
    }

}
