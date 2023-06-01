package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.dto.RoleDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoleCompleteReadDtoMapper implements Mapper<Role, RoleDto> {
    @Override
    public RoleDto map(Role from) {
        return RoleDto.builder()
                .id(from.getId())
                .name(from.getName())
                .code(from.getCode())
                .build();
    }
}
