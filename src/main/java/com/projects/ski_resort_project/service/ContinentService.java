package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.ContinentReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Continent;
import com.projects.ski_resort_project.repository.jpa.ContinentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContinentService {
    private final ContinentRepository continentRepository;
    private final Mapper<Continent, ContinentReadDto> continentToContinentReadDtoMapper;

    public List<ContinentReadDto> getContinents(String search) {
        return continentToContinentReadDtoMapper.map(
                continentRepository.findByNameContainingIgnoreCase(search)
        );
    }
}
