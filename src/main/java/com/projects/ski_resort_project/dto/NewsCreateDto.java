package com.projects.ski_resort_project.dto;

import org.springframework.web.multipart.MultipartFile;

public record NewsCreateDto(
        String title,
        String description,
        MultipartFile image,
        String imageAlt
) {
}
