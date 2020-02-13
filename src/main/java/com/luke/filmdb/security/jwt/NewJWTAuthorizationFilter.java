//package com.luke.filmdb.security.jwt;
//
//import io.jsonwebtoken.Jwts;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//import static com.luke.filmdb.config.SecurityConstants.HEADER_STRING;
//import static com.luke.filmdb.config.SecurityConstants.TOKEN_PREFIX;
//import static com.luke.filmdb.security.jwt.TokenProvider.ACCESS_TOKEN_VALIDITY_SECONDS;
//import static com.luke.filmdb.security.jwt.TokenProvider.getSigningKey;
//import static io.jsonwebtoken.SignatureAlgorithm.HS256;
//
//public class NewJWTAuthorizationFilter extends BasicAuthenticationFilter {
//
//    private TokenProvider tokenProvider;
//
//    public NewJWTAuthorizationFilter(AuthenticationManager authenticationManager,
//                                     TokenProvider tokenProvider) {
//        super(authenticationManager);
//        this.tokenProvider = tokenProvider;
//    }
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req,
//                                    HttpServletResponse res,
//                                    FilterChain chain) throws IOException, ServletException {
//        String header = req.getHeader(HEADER_STRING);
//
//        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
//            chain.doFilter(req, res);
//            return;
//        }
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(req, res);
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        String token = request.getHeader(HEADER_STRING).replace("\"", "");
//        if (token != null) {
//            // parse the token.
//
//            String user = Jwts.builder()
//                    .signWith(HS256, getSigningKey())
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
//                    .compact();
//
//            if (user != null) {
//                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//            }
//            return null;
//        }
//        return null;
//    }
//}