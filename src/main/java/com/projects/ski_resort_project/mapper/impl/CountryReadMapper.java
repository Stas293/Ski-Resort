package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.CountryReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryReadMapper implements Mapper<Country, CountryReadDto> {
    @Override
    public CountryReadDto map(Country from) {
        return CountryReadDto.builder()
                .id(from.getId())
                .continent(from.getContinent().getName())
                .country(from.getName())
                .build();
    }
}
