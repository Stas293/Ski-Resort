package com.projects.ski_resort_project.dto;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public record EventReadDto(
        Long id,
        String title,
        String description,
        @DateTimeFormat(pattern = "MM.dd.yyyy. HH:mm a")
        LocalDateTime date,
        String image,
        String imageAlt) {
}
