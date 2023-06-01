package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.ResortCreateDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Resort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class ResortCreateDtoMapper implements Mapper<ResortCreateDto, Resort> {
    @Override
    public Resort map(ResortCreateDto from) {
        Resort resort = Resort.builder()
                .title(from.title())
                .description(from.description())
                .imageAlt(from.imageAlt())
                .build();
        Optional.ofNullable(from.image())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> resort.setImage(image.getOriginalFilename())
                );
        return resort;
    }
}
