package com.projects.ski_resort_project.http.rest;

import com.projects.ski_resort_project.dto.EventFeedbackReadDto;
import com.projects.ski_resort_project.dto.ResortFeedbackReadDto;
import com.projects.ski_resort_project.service.EventFeedbackService;
import com.projects.ski_resort_project.service.ResortFeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/feedback")
@Slf4j
public class FeedbackRestController {
    private final ResortFeedbackService resortFeedbackService;
    private final EventFeedbackService eventFeedbackService;

    @GetMapping("/resorts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ResortFeedbackReadDto>> getResorts(@PathVariable Long id,
                                                                @RequestParam(defaultValue = "5") int limit,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "") String search,
                                                                @RequestParam(defaultValue = "ASC") String sorting) {
        return ResponseEntity.ok(resortFeedbackService.getFeedBacks(id, limit, page, search, sorting));
    }

    @GetMapping("/resorts")
    @ResponseStatus(HttpStatus.OK)
    public Page<ResortFeedbackReadDto> getEvents(@RequestParam(defaultValue = "5") int limit,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "") String search,
                                                                 @RequestParam(defaultValue = "ASC") String sorting) {
        return resortFeedbackService.getFeedBacks(limit, page, search, sorting);
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<EventFeedbackReadDto>> getEvents(@PathVariable Long id,
                                                                @RequestParam(defaultValue = "5") int limit,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "") String search,
                                                                @RequestParam(defaultValue = "ASC") String sorting) {
        return ResponseEntity.ok(eventFeedbackService.getFeedBacks(id, limit, page, search, sorting));
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public Page<EventFeedbackReadDto> getResorts(@RequestParam(defaultValue = "5") int limit,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "") String search,
                                                                 @RequestParam(defaultValue = "ASC") String sorting) {
        return eventFeedbackService.getFeedBacks(limit, page, search, sorting);
    }
}
