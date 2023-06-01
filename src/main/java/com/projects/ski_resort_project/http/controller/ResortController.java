package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.FeedbackCreateDto;
import com.projects.ski_resort_project.dto.ResortCreateDto;
import com.projects.ski_resort_project.dto.ResortReadCompleteDto;
import com.projects.ski_resort_project.service.ResortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.projects.ski_resort_project.enums.Pages.*;

@Controller
@RequestMapping("/resorts")
@RequiredArgsConstructor
@Slf4j
public class ResortController {
    private final ResortService resortService;

    @GetMapping("/north_america")
    public String getNorthAmerica() {
        return NORTH_AMERICA_PAGE.getPage();
    }

    @GetMapping("/south_america")
    public String getSouthAmerica() {
        return SOUTH_AMERICA_PAGE.getPage();
    }

    @GetMapping("/other_world")
    public String getOtherWorld() {
        return OTHER_WORLD_PAGE.getPage();
    }

    @GetMapping("/europe")
    public String getEurope() {
        return EUROPE_PAGE.getPage();
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id,
                             Model model,
                             @ModelAttribute("feedback") FeedbackCreateDto feedback) {
        Optional<ResortReadCompleteDto> resort = resortService.getResort(id);
        if (resort.isEmpty()) {
            return RESORT_REDIRECT.getPage();
        }
        model.addAttribute("resort", resort.get());
        return RESORT_ARTICLE_PAGE.getPage();
    }

    @GetMapping("/admin")
    public String getAdminResorts(){
        log.info("Get resort");
        return ADMIN_RESORT_PAGE.getPage();
    }

    @PostMapping("/admin")
    public String addResort(ResortCreateDto resortCreateDto) {
        log.info("Add resort");
        resortService.addResort(resortCreateDto);
        return "redirect:/resorts/admin";
    }
}
