package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.CityCreateDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.City;
import org.springframework.stereotype.Component;

@Component
public class CityCreateMapper implements Mapper<CityCreateDto, City> {
    @Override
    public City map(CityCreateDto from) {
        return City.builder()
                .name(from.name())
                .code(from.code())
                .build();
    }
}
