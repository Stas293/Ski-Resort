package com.projects.ski_resort_project.http.rest;

import com.projects.ski_resort_project.dto.NewsReadDto;
import com.projects.ski_resort_project.service.NewsService;
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
@RequestMapping("/api/v1/news")
@Slf4j
public class NewsRestController {
    private final NewsService newsService;
    
    @GetMapping
    public ResponseEntity<Page<NewsReadDto>> getNewsPage(@RequestParam(defaultValue = "5") int limit,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "") String search,
                                                         @RequestParam(defaultValue = "ASC") String sorting) {
        log.info("Get news page");
        return ResponseEntity.ok(newsService.getNewsPage(limit, page, search, sorting));
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> findImage(@PathVariable("id") String id) {
        return newsService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
