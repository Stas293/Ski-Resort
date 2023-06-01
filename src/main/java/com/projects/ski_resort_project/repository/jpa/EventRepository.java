package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.Event;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Cacheable("events")
    Page<Event> findAllByTitleContainingIgnoreCase(String search, Pageable pageable);

    @Cacheable("event")
    Optional<Event> findById(Long id);
}