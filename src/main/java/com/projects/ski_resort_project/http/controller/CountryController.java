package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.CountryCreateDto;
import com.projects.ski_resort_project.enums.Pages;
import com.projects.ski_resort_project.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/country")
@Slf4j
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping("/admin")
    public String getAdminCountry() {
        log.info("Get country");
        return Pages.ADMIN_COUNTRY_PAGE.getPage();
    }

    @PostMapping("/admin")
    public String postCountry(CountryCreateDto countryCreateDto) {
        log.info("Post country");
        countryService.createCountry(countryCreateDto);
        return "redirect:/country/admin";
    }
}
