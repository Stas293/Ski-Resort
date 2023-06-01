package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.Resort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResortRepository extends JpaRepository<Resort, Long>, ResortRepositoryQueries {
    @Override
    @EntityGraph(attributePaths = {"city", "city.country", "city.country.continent"})
    @Cacheable("resort")
    Optional<Resort> findById(Long aLong);
}