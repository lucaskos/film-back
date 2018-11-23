package com.example.demo;

import com.example.demo.config.model.TokenProperties;
import liquibase.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.example.demo.application.repository")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
				LogFactory.getInstance().getLog().setLogLevel("DEBUG");
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
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }
}
