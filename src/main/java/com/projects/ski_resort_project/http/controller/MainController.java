package com.projects.ski_resort_project.http.controller;

import com.projects.ski_resort_project.dto.*;
import com.projects.ski_resort_project.service.NewsService;
import com.projects.ski_resort_project.service.PublicService;
import com.projects.ski_resort_project.service.ResortService;
import com.projects.ski_resort_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.projects.ski_resort_project.enums.Attributes.NEWS;
import static com.projects.ski_resort_project.enums.Attributes.RESORTS;
import static com.projects.ski_resort_project.enums.Defaults.DEFAULT_PAGE_NUMBER;
import static com.projects.ski_resort_project.enums.Defaults.DEFAULT_PAGE_SIZE;
import static com.projects.ski_resort_project.enums.Pages.INDEX_PAGE;

@Controller
@SessionAttributes("reset")
@RequiredArgsConstructor
public class MainController {
    private final ResortService resortService;
    private final NewsService newsService;
    private final PublicService publicService;
    private final UserService userService;

    @GetMapping("/")
    public String getIndex(Model model) {
        int page = DEFAULT_PAGE_NUMBER.getValue();
        int size = DEFAULT_PAGE_SIZE.getValue();
        CompletableFuture<Page<ResortReadDto>> resorts = CompletableFuture.supplyAsync(() -> resortService.getResorts(page, size));
        CompletableFuture<Page<NewsReadDto>> news = CompletableFuture.supplyAsync(() -> newsService.getNews(page, size));

        model.addAttribute(NEWS.getValue(), news.join().getContent());
        model.addAttribute(RESORTS.getValue(), resorts.join().getContent());
        return INDEX_PAGE.getPage();
    }

    @GetMapping("/contact_us")
    public String getContactUs(@ModelAttribute("contactUs") ContactUsCreateDto contactUs) {
        return "contact_us";
    }

    @PostMapping("/contact_us")
    public String postContactUs(ContactUsCreateDto contactUs) {
        publicService.sendEmail(contactUs);
        return "redirect:/";
    }

    @GetMapping("/reset")
    public String getReset() {
        return "user/reset-password";
    }

    @PostMapping("/reset")
    public String postReset(RequestResetPasswordDto resetPasswordDto,
                            Model model) {
        Optional<UserReadDto> userReadDto = userService.resetPassword(resetPasswordDto);
        if (userReadDto.isEmpty()) {
            return "redirect:/reset";
        }
        model.addAttribute("reset", resetPasswordDto);
        return "redirect:/";
    }

    @GetMapping("/reset-password")
    public String getResetPassword(@SessionAttribute("reset") RequestResetPasswordDto resetPasswordDto,
                                   SessionStatus sessionStatus,
                                   ResetPasswordDto resetPassword,
                                   BindingResult bindingResult,
                                   Model model) {
        if (resetPasswordDto == null) {
            return "redirect:/reset";
        }
        NewPasswordResetDto newPasswordResetDto = userService.validateResetPassword(resetPasswordDto, resetPassword, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/reset";
        }
        model.addAttribute("resetPassword", newPasswordResetDto);
        sessionStatus.setComplete();
        return "user/user-reset";
    }

    @PostMapping("/reset-password")
    public String postResetPassword(@ModelAttribute("resetPassword") NewPasswordResetDto newPasswordResetDto) {
        userService.resetPassword(newPasswordResetDto);
        return "redirect:/login";
    }
}
