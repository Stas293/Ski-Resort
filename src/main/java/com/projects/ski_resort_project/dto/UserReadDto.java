package com.projects.ski_resort_project.dto;

import lombok.Builder;

/**
 * DTO for {@link com.projects.ski_resort_project.model.User}
 */
@Builder
public record UserReadDto(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String image) {
}
