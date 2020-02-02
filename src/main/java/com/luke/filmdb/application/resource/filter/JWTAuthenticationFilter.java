package com.luke.filmdb.application.resource.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.filmdb.application.DTO.user.AuthorizationCredentials;
import com.luke.filmdb.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.luke.filmdb.config.SecurityConstants.HEADER_STRING;
import static com.luke.filmdb.config.SecurityConstants.TOKEN_PREFIX;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AuthorizationCredentials creds = getUserCreds(req);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthorizationCredentials getUserCreds(HttpServletRequest req) throws IOException {
        return new ObjectMapper()
                .readValue(req.getInputStream(), AuthorizationCredentials.class);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        try {
            SecurityContextHolder.getContext().setAuthentication(auth);

            String accessToken = tokenProvider.generateToken(auth);

            res.addHeader(HEADER_STRING, TOKEN_PREFIX + accessToken);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
