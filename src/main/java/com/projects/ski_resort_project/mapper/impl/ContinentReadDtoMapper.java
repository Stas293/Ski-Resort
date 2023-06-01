package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.ContinentReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Continent;
import org.springframework.stereotype.Component;

@Component
public class ContinentReadDtoMapper implements Mapper<Continent, ContinentReadDto> {
    @Override
    public ContinentReadDto map(Continent from) {
        return ContinentReadDto.builder()
                .id(from.getId())
                .continent(from.getName())
                .build();
    }
}
