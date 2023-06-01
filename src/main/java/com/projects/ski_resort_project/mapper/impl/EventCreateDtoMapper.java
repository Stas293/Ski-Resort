package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.EventCreateDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Event;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class EventCreateDtoMapper implements Mapper<EventCreateDto, Event> {
    @Override
    public Event map(EventCreateDto from) {
        Event event = Event.builder()
                .title(from.title())
                .description(from.description())
                .date(from.date())
                .imageAlt(from.imageAlt())
                .build();
        Optional.ofNullable(from.image())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> event.setImage(image.getOriginalFilename())
                );
        return event;
    }
}
