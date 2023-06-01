package com.projects.ski_resort_project.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@RedisHash(value = "PasswordResetToken", timeToLive = 60 * 24)
public class PasswordResetToken implements BaseEntity<UUID> {
    @Id
    private UUID id;

    private String email;

    private User user;
}
