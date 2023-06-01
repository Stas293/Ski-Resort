package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.CityReadDto;
import com.projects.ski_resort_project.dto.ResortReadCompleteDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.City;
import com.projects.ski_resort_project.model.Resort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResortReadCompleteDtoMapper implements Mapper<Resort, ResortReadCompleteDto> {
    private final Mapper<City, CityReadDto> cityReadDtoMapper;

    @Override
    public ResortReadCompleteDto map(Resort from) {
        return ResortReadCompleteDto.builder()
                .id(from.getId())
                .title(from.getTitle())
                .description(from.getDescription())
                .image(from.getImage())
                .imageAlt(from.getImageAlt())
                .city(cityReadDtoMapper.map(from.getCity()))
                .build();
    }
}
