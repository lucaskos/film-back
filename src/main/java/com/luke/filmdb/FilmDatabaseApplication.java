package com.luke.filmdb;

import com.luke.filmdb.config.token.TokenProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.luke.application.repository")
public class FilmDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmDatabaseApplication.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix = "security.token")
    public TokenProperties tokenProperties() {
        return new TokenProperties();
    }

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${security.password.strength:10}") int strength) {
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return propertySourcesPlaceholderConfigurer;
    }
}
