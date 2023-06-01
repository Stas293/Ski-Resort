package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.CityCreateDto;
import com.projects.ski_resort_project.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.projects.ski_resort_project.enums.Pages.ADMIN_CITY_PAGE;

@Controller
@RequestMapping("/city")
@Slf4j
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping("/admin")
    public String getAdminCity() {
        log.info("Get city");
        return ADMIN_CITY_PAGE.getPage();
    }

    @PostMapping("/admin")
    public String postAdminCity(CityCreateDto cityCreateDto) {
        log.info("Post city");
        cityService.createCity(cityCreateDto);
        return "redirect:/city/admin";
    }
}
