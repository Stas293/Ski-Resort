package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.FeedbackCreateDto;
import com.projects.ski_resort_project.service.EventFeedbackService;
import com.projects.ski_resort_project.service.ResortFeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {
    private final ResortFeedbackService resortFeedbackService;
    private final EventFeedbackService eventFeedbackService;

    @PostMapping("/resort")
    public String addResortFeedback(@ModelAttribute @Validated FeedbackCreateDto feedback,
                              BindingResult bindingResult,
                              Long resortId,
                              RedirectAttributes redirectAttributes) {
        resortFeedbackService.save(feedback, resortId, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("feedback", feedback);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return String.format("redirect:/resorts/%d", resortId);
        }
        return String.format("redirect:/resorts/%d", resortId);
    }


    @PostMapping("/event")
    public String addEventFeedback(@ModelAttribute @Validated FeedbackCreateDto feedback,
                              BindingResult bindingResult,
                              Long eventId,
                              RedirectAttributes redirectAttributes) {
        eventFeedbackService.save(feedback, eventId, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("feedback", feedback);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/events/" + eventId;
        }
        return String.format("redirect:/events/%d", eventId);
    }
}
