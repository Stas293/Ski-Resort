package com.projects.ski_resort_project.mapper.impl;

import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.PersonalInfo;
import com.projects.ski_resort_project.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;

@Component
public class OidcUserMapperImpl implements Mapper<Map<String, Object>, User> {
    @Override
    public User map(Map<String, Object> from) {
        String email = (String) from.get("email");
        String username = StringUtils.capitalize(email.split("@")[0]);
        String password = UUID.randomUUID().toString();
        String givenName = (String) from.get("given_name");
        String familyName = (String) from.get("family_name");
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
