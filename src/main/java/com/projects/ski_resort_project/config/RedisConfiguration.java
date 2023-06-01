package com.projects.ski_resort_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "com.projects.ski_resort_project.repository.redis")
public class RedisConfiguration {
}
