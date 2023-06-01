package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.PersonalInfo;
import com.projects.ski_resort_project.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component("oAuth2Mapper")
@Slf4j
public class OAuth2UserMapper implements Mapper<Map<String, Object>, User> {
    @Override
    public User map(Map<String, Object> from) {
        String email = (String) from.get("email");
        String username = (String) from.get("login");
        String password = UUID.randomUUID().toString();
        String name = (String) from.get("name");
        String[] nameParts = name.split(" ");
        String givenName = nameParts[0];
        String familyName = nameParts[1];
        return User.builder()
                .username(username)
                .password(password)
                .oAuth2(true)
                .personalInfo(PersonalInfo.builder()
                        .firstname(givenName)
                        .lastname(familyName)
                        .email(email)
                        .build())
                .build();
    }
}
