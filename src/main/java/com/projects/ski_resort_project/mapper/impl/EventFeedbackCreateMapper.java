package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.FeedbackCreateDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.EventFeedback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventFeedbackCreateMapper implements Mapper<FeedbackCreateDto, EventFeedback> {
    @Override
    public EventFeedback map(FeedbackCreateDto from) {
        return EventFeedback.builder()
                .rating(from.rating())
                .message(from.message())
                .build();
    }
}
