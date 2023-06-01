package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.NewsReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewsReadDtoMapper implements Mapper<News, NewsReadDto> {
    @Override
    public NewsReadDto map(News from) {
        return NewsReadDto.builder()
                .id(from.getId())
                .title(from.getTitle())
                .description(from.getDescription())
                .date(from.getDate())
                .image(from.getImage())
                .imageAlt(from.getImageAlt())
                .build();
    }
}
