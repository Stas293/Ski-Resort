package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.EventFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventFeedbackRepository extends JpaRepository<EventFeedback, Long> {
    Page<EventFeedback> findAllByMessageContainingIgnoreCase(String search, Pageable pageRequest);

    Page<EventFeedback> findAllByMessageContainingIgnoreCaseAndEventId(String search, Long id, Pageable pageRequest);
}
