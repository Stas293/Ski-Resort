package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.ResortReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Resort;
import org.springframework.stereotype.Component;

@Component
public class ResortReadDtoMapper implements Mapper<Resort, ResortReadDto> {

    @Override
    public ResortReadDto map(Resort from) {
        return ResortReadDto.builder()
                .id(from.getId())
                .title(from.getTitle())
                .description(from.getDescription())
                .image(from.getImage())
                .imageAlt(from.getImageAlt())
                .build();
    }
}
