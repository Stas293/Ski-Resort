package com.projects.ski_resort_project.http.rest;

import com.projects.ski_resort_project.dto.CountryReadDto;
import com.projects.ski_resort_project.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CountryRestController {
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryReadDto>> getCountries(String search) {
        log.info("Get countries");
        return ResponseEntity.ok(countryService.getCountries(search));
    }
}
