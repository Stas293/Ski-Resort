package com.projects.ski_resort_project.dto;

import org.springframework.web.multipart.MultipartFile;

public record ResortCreateDto(
        String title,
        String description,
        Long items,
        MultipartFile image,
        String imageAlt
) {
}
