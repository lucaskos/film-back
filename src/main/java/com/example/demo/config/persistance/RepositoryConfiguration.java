package com.example.demo.config.persistance;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.validation.constraints.NotNull;

@Configuration
@EnableJpaRepositories({
        "com.example.demo.model"})
public class RepositoryConfiguration {
}
