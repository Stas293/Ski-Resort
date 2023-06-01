package com.projects.ski_resort_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record ContactUsCreateDto(
        String fullName,
        @Email
        String email,
        @Pattern(regexp = "^(\\+\\d{2})?\\d{10}$")
        String phoneNumber,
        String message
) {
}
