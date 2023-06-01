package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.UserReadDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapperImpl implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User from) {
        return UserReadDto.builder()
                .id(from.getId())
                .username(from.getUsername())
                .firstName(from.getPersonalInfo().getFirstname())
                .lastName(from.getPersonalInfo().getLastname())
                .email(from.getPersonalInfo().getEmail())
                .image(from.getPersonalInfo().getImage())
                .build();
    }
}
