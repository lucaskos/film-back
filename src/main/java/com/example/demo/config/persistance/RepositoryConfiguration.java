package com.example.demo.config.persistance;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories({
        "com.example.demo.application.model"})
public class RepositoryConfiguration {
}
