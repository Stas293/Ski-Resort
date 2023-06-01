package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.UserRegistrationDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.PersonalInfo;
import com.projects.ski_resort_project.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserRegistrationMapperImpl implements Mapper<UserRegistrationDto, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserRegistrationDto from) {
        User user = User.builder()
                .username(from.username())
                .password(passwordEncoder.encode(from.password()))
                .oAuth2(false)
                .personalInfo(PersonalInfo.builder()
                        .firstname(from.firstName())
                        .lastname(from.lastName())
                        .email(from.email())
                        .build())
                .build();
        Optional.ofNullable(from.image())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.getPersonalInfo()
                        .setImage(image.getOriginalFilename())
                );
        return user;
    }
}
