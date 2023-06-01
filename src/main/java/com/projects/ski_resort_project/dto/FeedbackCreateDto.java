package com.projects.ski_resort_project.dto;

import com.projects.ski_resort_project.model.ResortFeedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

/**
 * DTO for {@link ResortFeedback}
 */
public record FeedbackCreateDto(
        @NotBlank
        String message,

        @NotNull
        @Range(min = 1, max = 5)
        Integer rating) {
}
