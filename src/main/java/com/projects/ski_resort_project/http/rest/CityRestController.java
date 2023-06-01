package com.projects.ski_resort_project.http.rest;

import com.projects.ski_resort_project.dto.CityReadDto;
import com.projects.ski_resort_project.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CityRestController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityReadDto>> getCities(String search) {
        log.info("Get cities");
        return ResponseEntity.ok(cityService.getCities(search));
    }
}
