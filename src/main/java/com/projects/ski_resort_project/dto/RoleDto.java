package com.projects.ski_resort_project.dto;

import lombok.Builder;

@Builder
public record RoleDto (
        Long id,
        String name,
        String code) {
}
