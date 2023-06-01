package com.projects.ski_resort_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO for {@link com.projects.ski_resort_project.model.User}
 */
@Builder
public record UserRegistrationDto(
        @Pattern(regexp = "^[A-Za-z0-9]+$")
        @Size(min = 3, max = 255)
        String username,
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
        String password,
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

        String confirmPassword,
        @Pattern(regexp = "^[A-Z][a-z]+$")
        @Size(min = 3, max = 255)
        String firstName,
        @Pattern(regexp = "^[A-Z][a-z]+$")
        @Size(min = 3, max = 255)
        String lastName,
        @Pattern(regexp = "^\\+380\\d{9}$")
        String phone,
        @Email
        String email,
        MultipartFile image) {
}
