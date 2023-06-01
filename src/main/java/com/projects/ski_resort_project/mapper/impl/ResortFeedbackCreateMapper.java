package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.FeedbackCreateDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.ResortFeedback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResortFeedbackCreateMapper implements Mapper<FeedbackCreateDto, ResortFeedback> {
    @Override
    public ResortFeedback map(FeedbackCreateDto from) {
        return ResortFeedback.builder()
                .rating(from.rating())
                .message(from.message())
                .build();
    }
}
