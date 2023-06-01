package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.Country;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @EntityGraph(attributePaths = {"continent"})
    List<Country> findAllByNameContainingIgnoreCase(@NonNull String name);
}
