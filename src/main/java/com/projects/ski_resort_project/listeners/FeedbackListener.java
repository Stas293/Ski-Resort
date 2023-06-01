package com.projects.ski_resort_project.listeners;

import com.projects.ski_resort_project.model.Feedback;
import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class FeedbackListener {
    @PrePersist
    public void prePersist(Feedback feedback) {
        log.info("FeedbackListener.prePersist: {}", feedback);
        feedback.setDateCreated(LocalDate.now());
    }
}
