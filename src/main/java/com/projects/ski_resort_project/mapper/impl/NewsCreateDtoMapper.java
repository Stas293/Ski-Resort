package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.NewsCreateDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.News;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class NewsCreateDtoMapper implements Mapper<NewsCreateDto, News> {
//    String title,
//        String description,
//        MultipartFile image,
//        String imageAlt
    @Override
    public News map(NewsCreateDto from) {
        News news = News.builder()
                .title(from.title())
                .description(from.description())
                .imageAlt(from.imageAlt())
                .build();
        Optional.ofNullable(from.image())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> news.setImage(image.getOriginalFilename())
                );
        return news;
    }
}
