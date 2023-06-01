package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.EventReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventReadDtoMapper implements Mapper<Event, EventReadDto> {
    @Override
    public EventReadDto map(Event from) {
        return EventReadDto.builder()
                .id(from.getId())
                .title(from.getTitle())
                .date(from.getDate())
                .description(from.getDescription())
                .image(from.getImage())
                .imageAlt(from.getImageAlt())
                .build();
    }
}
