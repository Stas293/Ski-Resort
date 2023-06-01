package com.projects.ski_resort_project.validation;

import com.projects.ski_resort_project.dto.UserRegistrationDto;
import com.projects.ski_resort_project.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserRegistrationValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationDto user = (UserRegistrationDto) target;

        if (userRepository.findByUsername(user.username()).isPresent()) {
            errors.rejectValue("username", "username.already.exists");
        }

        if (userRepository.findByEmail(user.email()).isPresent()) {
            errors.rejectValue("email", "email.already.exists");
        }

        if (!user.password().equals(user.confirmPassword())) {
            errors.rejectValue("confirmPassword", "passwords.not.matching");
        }
    }
}
