package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.ResortFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResortFeedbackRepository extends JpaRepository<ResortFeedback, Long> {
    @EntityGraph(attributePaths = {"user"})
    Page<ResortFeedback> findAllByMessageContainingIgnoreCase(String message, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<ResortFeedback> findAllByMessageContainingIgnoreCaseAndResortId(String search, Long id, Pageable pageRequest);
}
