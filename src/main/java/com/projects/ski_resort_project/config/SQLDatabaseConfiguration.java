package com.projects.ski_resort_project.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.projects.ski_resort_project.repository.jpa")
@EnableCaching
public class SQLDatabaseConfiguration {
}
