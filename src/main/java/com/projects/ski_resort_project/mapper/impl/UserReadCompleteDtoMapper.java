package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.RoleDto;
import com.projects.ski_resort_project.dto.UserReadCompleteDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Role;
import com.projects.ski_resort_project.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserReadCompleteDtoMapper implements Mapper<User, UserReadCompleteDto> {
    private final Mapper<Role, RoleDto> roleDtoMapper;

    @Override
    public UserReadCompleteDto map(User from) {
        List<RoleDto> roleDtos = roleDtoMapper.map(from.getRoles());
        return UserReadCompleteDto.builder()
                .id(from.getId())
                .username(from.getUsername())
                .firstName(from.getPersonalInfo().getFirstname())
                .lastName(from.getPersonalInfo().getLastname())
                .email(from.getPersonalInfo().getEmail())
                .enabled(from.getEnabled())
                .oAuth2(from.getOAuth2())
                .roles(roleDtos)
                .build();
    }
}
