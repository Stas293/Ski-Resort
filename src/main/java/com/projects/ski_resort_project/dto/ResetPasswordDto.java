package com.projects.ski_resort_project.dto;

public record ResetPasswordDto(
        String token,
        String email
) {
}
