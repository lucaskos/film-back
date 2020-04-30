package com.luke.filmdb.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.filmdb.application.DTO.LoginDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        LoginDTO object;
        object = getObject(req);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        object.getUsername(),
                        object.getPassword(),
                        new ArrayList<>())
        );
    }

    private LoginDTO getObject(HttpServletRequest req) {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDTO loginDTO = null;

        try {
            String collect = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            loginDTO = objectMapper.readValue(collect, LoginDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("Error in parsing login request.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginDTO;
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req,
                                         HttpServletResponse res,
                                         FilterChain chain,
                                         Authentication auth) throws IOException, ServletException {
        super.successfulAuthentication(req, res, chain, auth);

        chain.doFilter(req, res);
    }

}
