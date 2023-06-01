package com.projects.ski_resort_project.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record EventCreateDto (
        String title,
        String description,
        LocalDateTime date,
        MultipartFile image,
        String imageAlt
) {
}
