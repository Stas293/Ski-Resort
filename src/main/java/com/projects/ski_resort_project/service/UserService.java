package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.*;
import com.projects.ski_resort_project.enums.Roles;
import com.projects.ski_resort_project.exceptions.UserRegistrationException;
import com.projects.ski_resort_project.mapper.Mapper;
import com.projects.ski_resort_project.model.PasswordResetToken;
import com.projects.ski_resort_project.model.Role;
import com.projects.ski_resort_project.model.User;
import com.projects.ski_resort_project.repository.jpa.RoleRepository;
import com.projects.ski_resort_project.repository.jpa.UserRepository;
import com.projects.ski_resort_project.repository.redis.PasswordResetRepository;
import com.projects.ski_resort_project.service.mail.EmailService;
import com.projects.ski_resort_project.validation.ValidatorFiltration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    @ValidatorFiltration
    private final Set<Validator> validators;
    private final Mapper<UserRegistrationDto, User> userRegistrationMapper;
    private final Mapper<UserUpdatePersonalDto, User> userUpdatePersonalMapper;
    private final Mapper<User, UserReadDto> userReadMapper;
    private final Mapper<User, UserUpdatePersonalDto> userUpdatePersonalReadMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final RoleRepository roleRepository;
    private final Mapper<User, UserDetails> userDetailsMapper;
    private final Mapper<User, UserReadCompleteDto> userReadCompleteMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordResetRepository passwordResetRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userDetailsMapper::map)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public UserReadDto registerUser(UserRegistrationDto user, BindingResult bindingResult) {
        validators.forEach(validator -> validator.validate(user, bindingResult));
        if (bindingResult.hasErrors()) {
            return null;
        }
        return Optional.of(user)
                .map(dto -> {
                    uploadImage(dto.image());
                    return userRegistrationMapper.map(dto);
                })
                .map(userToSave -> {
                    Role role = roleRepository.findByCode(Roles.ROLE_USER.name()).orElseThrow();
                    userToSave.getRoles().add(role);
                    return userToSave;
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow(() -> new UserRegistrationException("User registration failed"));
    }

    private void uploadImage(MultipartFile image) {
        Optional.ofNullable(image)
                .filter(file -> !file.isEmpty())
                .ifPresent(this::saveImage);
    }

    @SneakyThrows
    private void saveImage(MultipartFile multipartFile) {
        imageService.upload(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveNewUser(User newUser) {
        userRepository.save(newUser);
    }

    public UserReadDto getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(name)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    public Page<UserReadCompleteDto> getUsers(int limit, int page, String search, String sorting) {
        Pageable pageable = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.fromString(sorting), "username")
        );
        return userRepository.findAllByUsernameContainingIgnoreCase(search, pageable)
                .map(userReadCompleteMapper::map);
    }

    public UserReadCompleteDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userReadCompleteMapper::map)
                .orElseThrow();
    }


    @Transactional
    public void manageEnabled(Long id, boolean b) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setEnabled(b);
                    userRepository.save(user);
                });
    }

    @Transactional
    public void updateUser(UserUpdateRolesDto userDto) {
        List<Role> roles = roleRepository.findAllById(userDto.roles());
        userRepository.findById(userDto.id())
                .ifPresent(user -> {
                    user.setRoles(roles);
                    userRepository.save(user);
                });
    }

    @Transactional
    public void updateUser(UserUpdatePersonalDto userDto, BindingResult bindingResult) {
        User user = userRepository.findById(userDto.id()).orElseThrow();
        if (!passwordEncoder.matches(userDto.oldPassword(), user.getPassword())) {
            bindingResult.rejectValue("oldPassword", "passwords.not.matching");
        }
        if (!userDto.password().equals(userDto.confirmPassword())) {
            bindingResult.rejectValue("password", "passwords.not.matching");
        }
        if (bindingResult.hasErrors()) {
            return;
        }
        userUpdatePersonalMapper.map(userDto, user);
        userRepository.save(user);
    }

    public UserUpdatePersonalDto getUserUpdate() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(name)
                .map(userUpdatePersonalReadMapper::map)
                .orElseThrow();
    }

    public Optional<UserReadDto> resetPassword(RequestResetPasswordDto resetPasswordDto) {
        Optional<User> user = userRepository.findByEmail(resetPasswordDto.email());
        if (user.isEmpty()) {
            return Optional.empty();
        }
        UUID uuid = UUID.randomUUID();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .id(uuid)
                .user(user.get())
                .build();
        passwordResetRepository.save(passwordResetToken);
        String message = String.format("We received a request to reset your password on Ski Resort Website. " +
                "Please click the link below to reset your password. " +
                "http://localhost:8080/reset-password?token=%s&email=%s", uuid, resetPasswordDto.email());
        emailService.sendSimpleMessage(
                resetPasswordDto.email(),
                "We received a request to reset your password. We are here to help!",
                message);
        return Optional.of(userReadMapper.map(user.get()));
    }

    public NewPasswordResetDto validateResetPassword(RequestResetPasswordDto resetPasswordDto,
                                      ResetPasswordDto resetPassword,
                                      BindingResult bindingResult) {
        if (resetPasswordDto.email().isEmpty()) {
            bindingResult.rejectValue("email", "email.empty");
        }
        if (!resetPassword.email().equals(resetPasswordDto.email())) {
            bindingResult.rejectValue("email", "email.not.matching");
        }
        if (resetPassword.token().isEmpty()) {
            bindingResult.rejectValue("token", "token.empty");
        }
        Optional<PasswordResetToken> byId = passwordResetRepository.findById(UUID.fromString(resetPassword.token()));
        if (byId.isEmpty()) {
            bindingResult.rejectValue("token", "token.not.found");
        }
        User user = byId.get().getUser();
        return NewPasswordResetDto.builder()
                .id(user.getId())
                .build();
    }

    public void resetPassword(NewPasswordResetDto newPasswordResetDto) {
        User user = userRepository.findById(newPasswordResetDto.id()).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPasswordResetDto.password()));
        userRepository.save(user);
    }
}
