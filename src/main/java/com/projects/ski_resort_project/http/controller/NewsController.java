package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.NewsReadDto;
import com.projects.ski_resort_project.dto.NewsCreateDto;
import com.projects.ski_resort_project.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static com.projects.ski_resort_project.enums.Pages.*;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
@Slf4j
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public String getNews() {
        return NEWS_PAGE.getPage();
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable String id, Model model) {
        Optional<NewsReadDto> news = newsService.getNews(id);
        if (news.isEmpty()) {
            return NEWS_REDIRECT.getPage();
        }
        log.info("News: {}", news.get());
        model.addAttribute("news", news.get());
        return NEWS_ARTICLE_PAGE.getPage();
    }

    @GetMapping("/admin")
    public String getAdminNews() {
        log.info("Get news");
        return ADMIN_NEWS_PAGE.getPage();
    }

    @PostMapping("/admin")
    public String addNews(NewsCreateDto newsCreateDto) {
        log.info("Add news");
        newsService.addNews(newsCreateDto);
        return "redirect:/news/admin";
    }
}
