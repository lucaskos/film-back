package com.example.demo.security;

import com.example.demo.common.MyUserPrincipal;
import com.example.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.example.demo.config.SecurityConstants.SECRET;
import static com.example.demo.config.SecurityConstants.TOKEN_PREFIX;

@Component
public class JwtUtil {

    @Value("${security.token.secret}")
    private String secret;

    public UsernamePasswordAuthenticationToken getUserFromToken(String token) {
       String user = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }

    public String generateToken(Authentication authentication) {
        MyUserPrincipal u = (MyUserPrincipal) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getUser().getId() + "");
        claims.put("role", u.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
