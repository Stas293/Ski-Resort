package com.projects.ski_resort_project.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
@Builder
public class PersonalInfo implements Serializable {
    private String firstname;

    private String lastname;

    private String email;

    private LocalDate dateOfCreation;

    private String image;
}
