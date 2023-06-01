package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.UserUpdatePersonalDto;
import com.projects.ski_resort_project.dto.UserUpdateRolesDto;
import com.projects.ski_resort_project.service.RoleService;
import com.projects.ski_resort_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.projects.ski_resort_project.enums.Pages.ADMIN_USER_LIST_PAGE;
import static com.projects.ski_resort_project.enums.Pages.ADMIN_USER_PAGE;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/personal")
    public String getPersonalPage(Model model) {
        log.info("Get personal page");
        model.addAttribute("user", userService.getUser());
        return "user/personal";
    }

    @GetMapping("/admin")
    public String getAdminUser() {
        log.info("Get user");
        return ADMIN_USER_LIST_PAGE.getPage();
    }

    @GetMapping("/{id}/admin")
    public String getAdminUserById(@PathVariable Long id,
                                   Model model) {
        log.info("Get user by id");
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getRoles());
        return ADMIN_USER_PAGE.getPage();
    }

    @PatchMapping("/admin/disable/{id}")
    public String disableUser(@PathVariable Long id) {
        log.info("Disable user");
        userService.manageEnabled(id, false);
        return "redirect:/user/admin";
    }
    
    @PatchMapping("/admin/enable/{id}")
    public String enableUser(@PathVariable Long id) {
        log.info("Enable user");
        userService.manageEnabled(id, true);
        return "redirect:/user/admin";
    }

    @PatchMapping("/admin")
    public String updateUser(UserUpdateRolesDto userDto) {
        log.info("Update user");
        userService.updateUser(userDto);
        return "redirect:/user/admin";
    }

    @GetMapping("/edit")
    public String editPersonalInfo(Model model) {
        log.info("Edit personal info");
        model.addAttribute("user", userService.getUserUpdate());
        return "user/edit";
    }

    @PutMapping("/edit")
    public String updatePersonalInfo(UserUpdatePersonalDto userDto,
                                     BindingResult bindingResult) {
        log.info("Update personal info");
        userService.updateUser(userDto, bindingResult);
        return "redirect:/user/personal";
    }
}
