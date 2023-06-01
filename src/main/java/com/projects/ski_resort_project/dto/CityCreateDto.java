package com.projects.ski_resort_project.dto;

import lombok.Builder;

@Builder
public record CityCreateDto (
        String name,
        String code,
        Long items
) {
}
