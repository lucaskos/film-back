package com.example.demo.common;


public class ConfigurationConstants {

    /** Datasource keys to read data from property file */
    public static final String DATASOURCE_PATH = "spring.datasource.driver-class-name";
    public static final String DATASOURCE_URL = "spring.datasource.url";
    public static final String DATASOURCE_USERNAME = "spring.datasource.username";
    public static final String DATASOURCE_PASSWORD = "spring.datasource.password";

    public static final String HIBERNATE_DIALECT_KEY = "spring.datasource.hibernate.dialect";
    public static final String HIBERNATE_HBM2DLL_KEY = "hibernate.hbm2ddl.auto";
    public static final String HIBERNATE_SHOW_SQL_KEY = "hibernate.show_sql";
    public static final String HIBERNATE_FORMAT_SQL_KEY = "hibernate.format_sql";
    public static final String HIBERNATE_DEFAULT_SCHEMA_KEY = "hibernate.default_schema";

    /** Values to be taken from external configuration file using YAML or properties file. */
    public static final String HIBERNATE_DIALECT_PROP_VAL = "spring.datasource.hibernate.dialect";
    public static final String HIBERNATE_HBM2DLL_PROP_VAL = "spring.datasource.hibernate.hbm2ddl.method";
    public static final String HIBERNATE_SHOW_SQL_PROP_VAL = "spring.datasource.hibernate.show_sql";
    public static final String HIBERNATE_FORMAT_SQL_PROP_VAL = "spring.datasource.hibernate.format_sql";
    public static final String HIBERNATE_DEFAULT_SCHEMA_PROP_VAL = "spring.jpa.properties.hibernate.default_schema";

    public static final String TEST_DATASOURCE_PATH = "test.dataSource.driver_class_name";
    public static final String TEST_DATASOURCE_URL = "test.dataSource.url";
    public static final String TEST_DATASOURCE_USERNAME = "test.dataSource.username";
    public static final String TEST_DATASOURCE_PASSWORD = "test.dataSource.password";

    public static final String STATIC_RESOURCES_NAME = "static.resources.name";
    public static final String STATIC_RESOURCES_PATH = "static.resources.path";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_PREMIUM = "ROLE_PREMIUM";

    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String ROLE_DUMMY = "ROLE_DUMMY";
}
