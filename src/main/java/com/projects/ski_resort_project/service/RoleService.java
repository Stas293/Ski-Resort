package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.RoleDto;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Role;
import com.projects.ski_resort_project.repository.jpa.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;
    private final Mapper<Role, RoleDto> roleMapper;

    public List<RoleDto> getRoles() {
        return roleMapper.map(roleRepository.findAll());
    }
}
