package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContinentRepository extends JpaRepository<Continent, Long> {

    List<Continent> findByNameContainingIgnoreCase(String search);
}
