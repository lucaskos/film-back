package com.luke.filmdb.security;

import com.luke.filmdb.application.services.CustomUserServiceImpl;
import com.luke.filmdb.security.jwt.JWTAuthenticationFilter;
import com.luke.filmdb.security.jwt.JWTAuthorizationFilter;
import com.luke.filmdb.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Luke on 24.10.2018.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class BasicAuthConfig extends WebSecurityConfigurerAdapter {

    private CustomUserServiceImpl customUserService;
    private TokenProvider tokenProvider;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/signin").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/**").authenticated()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessHandler(httpLogoutHandler)
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), tokenProvider))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");
//        web.ignoring().antMatchers("/token/**");
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(getPasswordEncoder());
    }
}
