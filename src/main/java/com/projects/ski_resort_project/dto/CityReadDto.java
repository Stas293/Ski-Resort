package com.projects.ski_resort_project.dto;

import lombok.Builder;

/**
 * DTO for {@link com.projects.ski_resort_project.model.City}
 */
@Builder
public record CityReadDto(
        Long id,
        String continent,
        String country,
        String city
) {
}
