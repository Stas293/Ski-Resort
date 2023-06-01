package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.UserUpdatePersonalDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserUpdatePersonalReadMapper implements Mapper<User, UserUpdatePersonalDto> {
    @Override
    public UserUpdatePersonalDto map(User from) {
        return UserUpdatePersonalDto.builder()
                .id(from.getId())
                .firstName(from.getPersonalInfo().getFirstname())
                .lastName(from.getPersonalInfo().getLastname())
                .email(from.getPersonalInfo().getEmail())
                .build();
    }
}
