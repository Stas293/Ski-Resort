package com.projects.ski_resort_project.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class News implements BaseEntity<String> {
    @Id
    private String id;

    private String title;

    private String description;

    private LocalDate date;

    private String image;

    private String imageAlt;
}
