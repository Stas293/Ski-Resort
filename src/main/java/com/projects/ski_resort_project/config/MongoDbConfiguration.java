package com.projects.ski_resort_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.projects.ski_resort_project.repository.mongo")
public class MongoDbConfiguration {
}
