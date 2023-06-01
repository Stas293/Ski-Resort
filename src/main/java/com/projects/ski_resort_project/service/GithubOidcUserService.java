package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.enums.Roles;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Role;
import com.projects.ski_resort_project.model.User;
import com.projects.ski_resort_project.repository.jpa.RoleRepository;
import com.projects.ski_resort_project.utility.FullNameInterface;
import com.projects.ski_resort_project.utility.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GithubOidcUserService extends DefaultOAuth2UserService {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final Mapper<User, UserDetails> userDetailsMapper;
    @Qualifier("oAuth2Mapper")
    private final Mapper<Map<String, Object>, User> oAuth2UserMapper;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        log.info("Access token: {}", accessToken.getTokenValue());
        String email = oAuth2User.getAttribute("email");
        Optional<User> user = userService.findUserByEmail(email);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        UserDetails userDetails = getUserDetails(attributes, user);
        FullNameInterface fullNameInterface = () -> (String) attributes.get("name");
        DefaultOAuth2User defaultOidcUser = new DefaultOAuth2User(
                userDetails.getAuthorities(),
                attributes,
        "login"
        );
        return Utility.getObjectImplementingMultipleInterfaces(
                userDetails,
                defaultOidcUser,
                fullNameInterface,
                OAuth2User.class
        );
    }

    private UserDetails getUserDetails(Map<String, Object> claims, Optional<User> user) {
        if (user.isPresent()) {
            if (Boolean.FALSE.equals(user.get().getEnabled())) {
                throw new OAuth2AuthenticationException("User is disabled");
            }
            return userDetailsMapper.map(user.get());
        } else {
            User newUser = oAuth2UserMapper.map(claims);
            Role role = roleRepository.findByCode(Roles.ROLE_USER.name())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            newUser.setRoles(List.of(role));
            userService.saveNewUser(newUser);
            return userDetailsMapper.map(newUser);
        }
    }
}
