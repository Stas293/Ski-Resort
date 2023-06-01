package com.projects.ski_resort_project.config;

import com.projects.ski_resort_project.enums.PublicAccessPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

import static com.projects.ski_resort_project.enums.Links.*;
import static com.projects.ski_resort_project.enums.Roles.ROLE_ADMIN;
import static com.projects.ski_resort_project.enums.Roles.ROLE_USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .requestMatchers(request ->
                                request.getServletPath().contains("admin")
                        ).hasRole(ROLE_ADMIN.toString())
                        .requestMatchers(request ->
                                request.getServletPath().contains("user")
                        ).hasRole(ROLE_USER.toString())
                        .requestMatchers(
                                configureAccessEnum(PublicAccessPaths.values())
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl(LOGOUT.getLink())
                        .logoutSuccessUrl(LOGIN.getLink())
                        .deleteCookies("JSESSIONID")
                )
                .formLogin(login -> login
                        .loginPage(LOGIN.getLink())
                        .defaultSuccessUrl(MAIN_PAGE.getLink())
                )
                .oauth2Login(config -> config
                        .loginPage(LOGIN.getLink())
                        .defaultSuccessUrl(MAIN_PAGE.getLink())
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService)
                                .userService(oauth2UserService)
                        )
                )
                .build();
    }

    private <T extends Enum<T>> String[] configureAccessEnum(Enum<T>[] values) {
        return Arrays.stream(values)
                .map(Enum::toString)
                .toArray(String[]::new);
    }
}
