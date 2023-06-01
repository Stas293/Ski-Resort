package com.projects.ski_resort_project.dto;


import lombok.Builder;

/**
 * DTO for {@link com.projects.ski_resort_project.model.Resort}
 */
@Builder
public record ResortReadCompleteDto(
        Long id,
        String title,
        String description,
        String image,
        String imageAlt,
        CityReadDto city
) {
}
