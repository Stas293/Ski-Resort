package com.projects.ski_resort_project.http.rest;

import com.projects.ski_resort_project.dto.UserReadCompleteDto;
import com.projects.ski_resort_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/users")
@Slf4j
public class UserRestController {
    private final UserService userService;

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserReadCompleteDto> getUserPage(@RequestParam(defaultValue = "5") int limit,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "") String search,
                                                 @RequestParam(defaultValue = "ASC") String sorting) {
        return userService.getUsers(limit, page, search, sorting);
    }
}
