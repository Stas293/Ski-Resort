package com.projects.ski_resort_project.repository.mongo;

import com.projects.ski_resort_project.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String> {
    Page<News> findAllByTitleIsLikeIgnoreCase(String search, Pageable date);
}
