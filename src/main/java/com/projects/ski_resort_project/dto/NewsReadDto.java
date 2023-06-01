package com.projects.ski_resort_project.dto;

import com.projects.ski_resort_project.model.News;
import lombok.Builder;

import java.time.LocalDate;

/**
 * DTO for {@link News}
 */
@Builder
public record NewsReadDto(
        String id,
        String title,
        String description,
        LocalDate date,
        String image,
        String imageAlt) {
}