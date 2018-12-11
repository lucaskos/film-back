package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@Profile("!production")
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.application.resource"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "\"DINGO :: JOTY :: OFFERS :: REST API\"",
                "Dokumentacja interfejsu RESTful'owego microserwisu Dingo Joty Offers",
                "0.1",
                "Terms of service",
                new Contact("Dingo Team", "", ""),
                "License of API", "", Collections.emptyList());
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration(null,
                null, null, null,
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QucGwiLCJyb2xlIjpbXSwidXNyIjoidGVzdCIsImlzcyI6IiR7c3ByaW5nLmFwcGxpY2F0aW9uLm5hbWV9IiwiZXhwIjoxNTQyNzU1NzEzLCJpYXQiOjE1NDI3NTU3MTN9.ZpIzekBxMxoYycZAAfcCI2LGk7e_nvyuMvd8BJ2fkws",
                ApiKeyVehicle.HEADER, "Authorization", ",");
    }
}
