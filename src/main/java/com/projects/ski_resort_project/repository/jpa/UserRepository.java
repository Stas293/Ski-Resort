package com.projects.ski_resort_project.repository.jpa;

import com.projects.ski_resort_project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.personalInfo.email = ?1")
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);

    Page<User> findAllByUsernameContainingIgnoreCase(String search, Pageable pageable);
}