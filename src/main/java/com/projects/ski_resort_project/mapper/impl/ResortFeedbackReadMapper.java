package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.ResortFeedbackReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.ResortFeedback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResortFeedbackReadMapper implements Mapper<ResortFeedback, ResortFeedbackReadDto> {
    @Override
    public ResortFeedbackReadDto map(ResortFeedback from) {
        return ResortFeedbackReadDto.builder()
                .message(from.getMessage())
                .rating(from.getRating())
                .dateCreated(from.getDateCreated())
                .fullName(from.getUser().getFullName())
                .build();
    }
}