package com.projects.ski_resort_project.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserReadCompleteDto(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        Boolean enabled,
        Boolean oAuth2,
        List<RoleDto> roles
) {
}
