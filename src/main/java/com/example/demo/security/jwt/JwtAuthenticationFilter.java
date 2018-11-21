package com.example.demo.security.jwt;

import com.example.demo.config.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.example.demo.config.SecurityConstants.*;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200");

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        if (logger.isDebugEnabled()) {
            logger.debug("Request method " + req.getServletPath());
        }

        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            List<GrantedAuthority> list = new ArrayList<>();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            list)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
        String origin = req.getHeader("Origin");
        res.setHeader("Access-Control-Allow-Headers", HEADER_STRING + ", " + "Content-Type, Accept, X-Requested-With, remember-me");
        res.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
        res.setHeader("Vary", "Origin");

        // Access-Control-Max-Age
        res.setHeader(MAX_AGE, MAX_AGE_TIME);

        // Access-Control-Allow-Credentials
        res.setHeader(ALLOW_CREDENTIALS, ALLOW_CREDENTIALS_VALUE);

        // Access-Control-Allow-Methods
        res.setHeader(METHODS_ALLOWED, "POST, GET, OPTIONS, DELETE");

        // Access-Control-Allow-Headers
        res.setHeader(HEADERS_ALLOWED,
                "Origin, Authorization, ROLES, X-Requested-By, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN");
        setExposedHeaders(res);

        if (!StringUtils.isEmpty(token) && auth.getAuthorities() != null && !auth.getAuthorities().isEmpty()) {
            addExposedHeaders(res, token, auth.getAuthorities());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Generated headers " + SecurityConstants.getGeneratedHeaders());
        }
    }

    private void setExposedHeaders(HttpServletResponse res) {
        res.addHeader(EXPOSED_HEADERS, HEADER_STRING + ", " + ROLES);
    }

    private void addExposedHeaders(HttpServletResponse res, String token, Collection<? extends GrantedAuthority> authorities) {
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.addHeader(ROLES, String.valueOf(authorities));
    }

}