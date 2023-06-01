package com.projects.ski_resort_project.http.rest;

import com.projects.ski_resort_project.dto.ResortReadDto;
import com.projects.ski_resort_project.service.ResortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/resorts")
@Slf4j
public class ResortsRestController {
    private final ResortService resortService;

    @GetMapping
    public ResponseEntity<Page<ResortReadDto>> getResortPage(@RequestParam(defaultValue = "5") int limit,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "") String search,
                                                             @RequestParam(defaultValue = "ASC") String sorting,
                                                             @RequestParam(defaultValue = "North America") String continent) {
        log.info("Get resorts page");
        log.info("Continent: " + continent);
        return ResponseEntity.ok(resortService.getResortPage(limit, page, search, sorting, continent));
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getResortImage(@PathVariable("id") Long id) {
        log.info("Get resort image");
        return resortService.getResortImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
