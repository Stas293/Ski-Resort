package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.CountryCreateDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryCreateDtoMapper implements Mapper<CountryCreateDto, Country> {
    @Override
    public Country map(CountryCreateDto from) {
        return Country.builder()
                .name(from.name())
                .code(from.code())
                .build();
    }
}
