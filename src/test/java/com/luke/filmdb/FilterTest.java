package com.luke.filmdb;

import com.luke.filmdb.security.AuthoritiesConstants;
import com.luke.filmdb.security.jwt.JWTAuthenticationFilter;
import com.luke.filmdb.security.jwt.JWTAuthorizationFilter;
import com.luke.filmdb.security.jwt.TokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {JWTAuthorizationFilter.class})
public class FilterTest {

    @Captor
    private ArgumentCaptor<SecurityContext> contextCaptor;

    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    AuthenticationManager authManager;
    @InjectMocks
    TokenProvider tokenProvider;

    JWTAuthorizationFilter authorizationFilter;
    JWTAuthenticationFilter authenticationFilter;

    final String OLD_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJzY29wZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE1ODE1NDI2OTgsImV4cCI6MTU4MTU2MDY5OH0.ZNXxxFDm969-QJIAzGE_1gwSbWwbya6unLMJ-QpNiOA";
    String username = "test-user";
    String password = "test-password";
    String freshToken;
    String role = "test-role";

    @Before
    public void init() {
        freshToken = this.tokenProvider.generateToken(new UsernamePasswordAuthenticationToken(
                this.username,
                this.password,
                Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))));
    }

    @Test
    public void generateTokenAssertNotNull() {
        String token = getToken();
        assertNotNull(token);
    }

//    @Test(expected = ExpiredJwtException.class)
//    public void getTokenExpired() {
//        String usernameFromToken = this.tokenProvider.getUsernameFromToken(OLD_TOKEN);
//
//        assertEquals(this.username, usernameFromToken);
//    }

    @Test
    public void getUsernameFromTokenAndAssertEqual() {
        String usernameFromToken = this.tokenProvider.getUsernameFromToken(freshToken);

        assertEquals(this.username, usernameFromToken);
    }

    @Test
    public void addBearerHeaderAndAuthorizeUser() throws ServletException, IOException {

        authorizationFilter = new JWTAuthorizationFilter(authManager, userDetailsService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = getToken();
        request.addHeader("Authorization", "Bearer " + token);
        request.setSession(new MockHttpSession());

        SimpleGrantedAuthority test = new SimpleGrantedAuthority("TEST");
        when(userDetailsService.loadUserByUsername(any())).thenReturn(new User(username, password, Collections.singleton(test)));

        final MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        authorizationFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void mockAuthorization() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/");
        request.addParameter(
                JWTAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY,
                password
        );
        request.addParameter(
                JWTAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY,
                username
        );
        JWTAuthenticationFilter jwtAuthorizationFilter = new JWTAuthenticationFilter(authManager, tokenProvider);
        Authentication authentication = new TestingAuthenticationToken(username, null, role);
        when(this.authManager.authenticate(any())).thenReturn(authentication);

        Authentication result = jwtAuthorizationFilter.attemptAuthentication(request,
                new MockHttpServletResponse());

        assertNotNull(result);
        assertEquals(result.getName(), username);
    }

    private String getToken() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                this.username,
                this.password,
                Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))
        );

        return this.tokenProvider.generateToken(authentication);
    }

    public User getUser() {
        return new User(username, password, null);
    }

    private AuthenticationManager createAuthenticationManager() {
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        return authenticationManager;
    }
}
