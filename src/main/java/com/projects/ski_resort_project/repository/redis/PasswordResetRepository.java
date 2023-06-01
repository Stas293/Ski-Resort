package com.projects.ski_resort_project.repository.redis;

import com.projects.ski_resort_project.model.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PasswordResetRepository extends CrudRepository<PasswordResetToken, UUID> {
}
