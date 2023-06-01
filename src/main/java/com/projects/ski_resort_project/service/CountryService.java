package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.CountryCreateDto;
import com.projects.ski_resort_project.dto.CountryReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Continent;
import com.projects.ski_resort_project.model.Country;
import com.projects.ski_resort_project.repository.jpa.ContinentRepository;
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
public class CountryService {
    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;
    private final Mapper<Country, CountryReadDto> countryToCountryReadDtoMapper;
    private final Mapper<CountryCreateDto, Country> countryCreateDtoToCountryMapper;


    public List<CountryReadDto> getCountries(String search) {
        return countryToCountryReadDtoMapper.map(
                countryRepository.findAllByNameContainingIgnoreCase(search)
        );
    }

    @Transactional
    public void createCountry(CountryCreateDto countryCreateDto) {
        Country country = countryCreateDtoToCountryMapper.map(countryCreateDto);
        Continent continent = continentRepository.findById(countryCreateDto.items())
                .orElseThrow();
        country.setContinent(continent);
        countryRepository.save(country);
    }
}
