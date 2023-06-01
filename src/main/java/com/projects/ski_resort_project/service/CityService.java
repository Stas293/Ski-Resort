package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.CityCreateDto;
import com.projects.ski_resort_project.dto.CityReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.City;
import com.projects.ski_resort_project.model.Country;
import com.projects.ski_resort_project.repository.jpa.CityRepository;
import com.projects.ski_resort_project.repository.jpa.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final Mapper<City, CityReadDto> cityMapper;
    private final Mapper<CityCreateDto, City> cityCreateMapper;

    public List<CityReadDto> getCities(String search) {
        return cityMapper.map(cityRepository.findAllByNameContainingIgnoreCase(search));
    }

    @Transactional
    public void createCity(CityCreateDto cityCreateDto) {
        City city = cityCreateMapper.map(cityCreateDto);
        Country country = countryRepository.findById(cityCreateDto.items()).orElseThrow();
        city.setCountry(country);
        cityRepository.save(city);
    }
}
