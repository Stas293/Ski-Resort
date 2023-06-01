package com.projects.ski_resort_project.http.rest;

import com.projects.ski_resort_project.dto.ContinentReadDto;
import com.projects.ski_resort_project.service.ContinentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/continent")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ContinentRestController {
    private final ContinentService continentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContinentReadDto> getContinents(String search) {
        log.info("Get continents");
        return continentService.getContinents(search);
    }
}
