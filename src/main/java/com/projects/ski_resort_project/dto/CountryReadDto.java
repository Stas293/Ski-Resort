package com.projects.ski_resort_project.dto;

import lombok.Builder;

@Builder
public record CountryReadDto(
        long id,
        String continent,
        String country
) {
}
