package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.UserRegistrationDto;
import com.projects.ski_resort_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.projects.ski_resort_project.enums.Attributes.*;
import static com.projects.ski_resort_project.enums.Links.*;
import static com.projects.ski_resort_project.enums.Pages.*;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        log.info("Get login page");
        return AUTH_LOGIN_PAGE.getPage();
    }

    @GetMapping("/registration")
    public String getRegistration(Model model,
                                  @ModelAttribute UserRegistrationDto user) {
        log.info("Get registration");
        model.addAttribute(USER.getValue(), user);
        return AUTH_REGISTRATION_PAGE.getPage();
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute @Validated UserRegistrationDto user,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        log.info("Post registration");
        userService.registerUser(user, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(USER.getValue(), user);
            redirectAttributes.addFlashAttribute(ERRORS.getValue(), bindingResult.getAllErrors());
            return REDIRECT_REGISTRATION_PAGE.getLink();
        }
        return REDIRECT_LOGIN_PAGE.getLink();
    }
}
