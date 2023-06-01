package com.projects.ski_resort_project.dto;

import lombok.Builder;

@Builder
public record UserUpdatePersonalDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String oldPassword,
        String password,
        String confirmPassword
) {
}
