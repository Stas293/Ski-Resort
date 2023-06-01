package com.projects.ski_resort_project.dto;

import java.util.List;

public record UserUpdateRolesDto(
        Long id,
        List<Long> roles
) {
}
