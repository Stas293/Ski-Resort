package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.City;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @EntityGraph(attributePaths = {"country", "country.continent"})
    List<City> findAllByNameContainingIgnoreCase(String search);
}
