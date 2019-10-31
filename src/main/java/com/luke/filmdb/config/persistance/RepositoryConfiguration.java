package com.luke.filmdb.config.persistance;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories({
        "com.luke.filmdb.application.repository"})
public class RepositoryConfiguration {
}
