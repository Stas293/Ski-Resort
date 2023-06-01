package com.projects.ski_resort_project.http.handler;

import com.projects.ski_resort_project.exceptions.UserRegistrationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserRegistrationException.class)
    public String handleUserRegistrationException(UserRegistrationException e) {
        return "redirect:/users/register?error=" + e.getMessage();
    }
}
