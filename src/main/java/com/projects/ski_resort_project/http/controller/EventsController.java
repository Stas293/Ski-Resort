package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.EventCreateDto;
import com.projects.ski_resort_project.dto.EventReadDto;
import com.projects.ski_resort_project.dto.FeedbackCreateDto;
import com.projects.ski_resort_project.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.projects.ski_resort_project.enums.Pages.*;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventsController {
    private final EventService eventService;

    @GetMapping()
    public String getEvents(){
        return EVENTS_PAGE.getPage();
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id,
                             Model model,
                             @ModelAttribute("feedback") FeedbackCreateDto feedbackCreateDto) {
        Optional<EventReadDto> event = eventService.getEventById(id);
        if (event.isEmpty()) {
            return EVENTS_REDIRECT_PAGE.getPage();
        }
        log.info("Got event: {}", event.get());
        model.addAttribute("event", event.get());
        return EVENTS_ARTICLE_PAGE.getPage();
    }

    @GetMapping("/admin")
    public String getAdminEvents(){
        log.info("Get event");
        return ADMIN_EVENT_PAGE.getPage();
    }

    @PostMapping("/admin")
    public String postAdminEvents(EventCreateDto eventDto){
        log.info("Post event");
        eventService.createEvent(eventDto);
        return "redirect:/events/admin";
    }
}
