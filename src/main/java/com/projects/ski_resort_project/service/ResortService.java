package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.ResortCreateDto;
import com.projects.ski_resort_project.dto.ResortReadCompleteDto;
import com.projects.ski_resort_project.dto.ResortReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.City;
import com.projects.ski_resort_project.model.Resort;
import com.projects.ski_resort_project.repository.jpa.CityRepository;
import com.projects.ski_resort_project.repository.jpa.ResortRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResortService {
    private final ResortRepository resortRepository;
    private final ImageService imageService;
    private final Mapper<Resort, ResortReadDto> resortReadDtoMapper;
    private final Mapper<Resort, ResortReadCompleteDto> resortResortReadCompleteDtoMapper;
    private final Mapper<ResortCreateDto, Resort> resortCreateDtoMapper;
    private final CityRepository cityRepository;

    public Page<ResortReadDto> getResorts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("title"));
        return resortRepository.findAll(pageRequest)
                .map(resortReadDtoMapper::map);
    }

    public Page<ResortReadDto> getResortPage(int limit, int page, String search, String sorting, String continent) {
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.valueOf(sorting), "title")
        );
        return resortRepository.findByContinentAndTitle(continent, search, pageRequest)
                .map(resortReadDtoMapper::map);
    }

    public Optional<byte[]> getResortImage(Long id) {
        return resortRepository.findById(id)
                .map(Resort::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

    public Optional<ResortReadCompleteDto> getResort(Long id) {
        return resortRepository.findById(id)
                .map(resortResortReadCompleteDtoMapper::map);
    }

    @Transactional
    public void saveResort(Resort resort) {
        resortRepository.save(resort);
    }

    @Transactional
    public ResortReadDto addResort(ResortCreateDto resortCreateDto) {
        Resort event = resortCreateDtoMapper.map(resortCreateDto);
        uploadImage(resortCreateDto.image());
        City city = cityRepository.findById(resortCreateDto.items()).orElseThrow();
        event.setCity(city);
        resortRepository.save(event);
        return resortReadDtoMapper.map(event);
    }

    private void uploadImage(MultipartFile image) {
        Optional.ofNullable(image)
                .filter(file -> !file.isEmpty())
                .ifPresent(this::saveImage);
    }

    @SneakyThrows
    private void saveImage(MultipartFile multipartFile) {
        imageService.upload(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
    }
}
