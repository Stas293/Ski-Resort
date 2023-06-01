package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.EventFeedbackReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.EventFeedback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventFeedbackReadMapper implements Mapper<EventFeedback, EventFeedbackReadDto> {
    @Override
    public EventFeedbackReadDto map(EventFeedback from) {
        return EventFeedbackReadDto.builder()
                .message(from.getMessage())
                .rating(from.getRating())
                .dateCreated(from.getDateCreated())
                .fullName(from.getUser().getFullName())
                .build();
    }
}
