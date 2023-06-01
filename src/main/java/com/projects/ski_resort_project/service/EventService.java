package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.EventCreateDto;
import com.projects.ski_resort_project.dto.EventReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Event;
import com.projects.ski_resort_project.repository.jpa.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ImageService imageService;
    private final Mapper<Event, EventReadDto> eventReadDtoMapper;
    private final Mapper<EventCreateDto, Event> eventCreateDtoMapper;


    public Optional<byte[]> findImage(Long id) {
        return eventRepository.findById(id)
                .map(Event::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

    public Page<EventReadDto> getEvents(int limit, int page, String search, String sorting) {
        Pageable pageable = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.fromString(sorting), "date")
        );
        return eventRepository.findAllByTitleContainingIgnoreCase(search, pageable)
                .map(eventReadDtoMapper::map);
    }

    public Optional<EventReadDto> getEventById(Long id) {
        return eventRepository.findById(id)
                .map(eventReadDtoMapper::map);
    }

    @Transactional
    public EventReadDto createEvent(EventCreateDto eventDto) {
        Event event = eventCreateDtoMapper.map(eventDto);
        uploadImage(eventDto.image());
        eventRepository.save(event);
        return eventReadDtoMapper.map(event);
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
