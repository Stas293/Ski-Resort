package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.Resort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResortRepositoryQueries {
    Page<Resort> findByContinentAndTitle(String continent,
                                         String title,
                                         Pageable pageable);
}
