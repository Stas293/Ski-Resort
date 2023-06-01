package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.CityReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.City;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CityReadDtoMapper implements Mapper<City, CityReadDto> {
    @Override
    public CityReadDto map(City from) {
        return CityReadDto.builder()
                .id(from.getId())
                .continent(from.getCountry().getContinent().getName())
                .country(from.getCountry().getName())
                .city(from.getName())
                .build();
    }
}
