package com.projects.ski_resort_project.dto;

import com.projects.ski_resort_project.model.ResortFeedback;
import lombok.Builder;

import java.time.LocalDate;

/**
 * DTO for {@link ResortFeedback}
 */
@Builder
public record EventFeedbackReadDto(
        String message,
        Integer rating,
        LocalDate dateCreated,
        String fullName
) {
}
