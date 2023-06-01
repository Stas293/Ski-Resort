package com.projects.ski_resort_project.listeners;

import com.projects.ski_resort_project.model.User;
import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class UserListener {
    @PrePersist
    public void prePersist(User user) {
        log.info("UserListener.prePersist: {}", user);
        user.getPersonalInfo().setDateOfCreation(LocalDate.now());
        user.setEnabled(true);
    }
}
