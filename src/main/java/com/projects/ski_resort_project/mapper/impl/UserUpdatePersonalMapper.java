package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.UserUpdatePersonalDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.PersonalInfo;
import com.projects.ski_resort_project.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUpdatePersonalMapper implements Mapper<UserUpdatePersonalDto, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserUpdatePersonalDto from) {
        return User.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname(from.firstName())
                        .lastname(from.lastName())
                        .email(from.email())
                        .build())
                .password(passwordEncoder.encode(from.password()))
                .build();

    }

    @Override
    public User map(UserUpdatePersonalDto from, User to) {
        to.getPersonalInfo().setFirstname(from.firstName());
        to.getPersonalInfo().setLastname(from.lastName());
        to.getPersonalInfo().setEmail(from.email());
        to.setPassword(passwordEncoder.encode(from.password()));
        return to;
    }
}
