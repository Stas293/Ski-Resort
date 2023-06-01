package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.enums.Roles;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.Role;
import com.projects.ski_resort_project.model.User;
import com.projects.ski_resort_project.repository.jpa.RoleRepository;
import com.projects.ski_resort_project.utility.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final Mapper<User, UserDetails> userDetailsMapper;
    @Qualifier("oidcUserMapperImpl")
    private final Mapper<Map<String, Object>, User> oidcUserMapper;

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcIdToken idToken = userRequest.getIdToken();
        String email = idToken.getClaim("email");
        Optional<User> user = userService.findUserByEmail(email);
        UserDetails userDetails = getUserDetails(idToken, user);
        DefaultOidcUser defaultOidcUser = new DefaultOidcUser(
                userDetails.getAuthorities(),
                userRequest.getIdToken());
        return Utility.getObjectImplementingMultipleInterfaces(
                userDetails,
                defaultOidcUser,
                OidcUser.class
        );
    }

    private UserDetails getUserDetails(OidcIdToken idToken, Optional<User> user) {
        if (user.isPresent()) {
            if (Boolean.FALSE.equals(user.get().getEnabled())) {
                throw new OAuth2AuthenticationException("User is not enabled");
            }
            return userDetailsMapper.map(user.get());
        } else {
            Map<String, Object> claims = idToken.getClaims();
            User newUser = oidcUserMapper.map(claims);
            Role role = roleRepository.findByCode(Roles.ROLE_USER.name())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            newUser.setRoles(List.of(role));
            userService.saveNewUser(newUser);
            return userDetailsMapper.map(newUser);
        }
    }
}
