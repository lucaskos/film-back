package com.luke.filmdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Properties;

//@Configuration
//@ConfigurationProperties("mysql")
//@EnableTransactionManagement
//public class MySqlConnectionConfig {
//
//    @NotNull
//    private String username;
//
//    @NotNull
//    private String minConnections;
//
//    @NotNull
//    private String maxConnections;
//
//    @NotNull
//    private String password;
//
//    @NotNull
//    private String url;
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setMinConnections(String minConnections) {
//        this.minConnections = minConnections;
//    }
//
//    public void setMaxConnections(String maxConnections) {
//        this.maxConnections = maxConnections;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

//    @Autowired
//    @Bean
//    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setEntityManagerFactory(emf);
//        return jpaTransactionManager;
//    }

//}
