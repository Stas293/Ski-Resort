package com.projects.ski_resort_project.service;

import com.projects.ski_resort_project.dto.ContactUsCreateDto;
import com.projects.ski_resort_project.service.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublicService {
    private final EmailService emailService;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void sendEmail(ContactUsCreateDto contactUs) {
        executorService.submit(() -> {
            sendEmailToUser(contactUs);
            sendEmailToAdmin(contactUs);
        });
    }

    private void sendEmailToAdmin(ContactUsCreateDto contactUs) {
        String subject = String.format("Contact us message from %s (%s)", contactUs.fullName(), contactUs.email());
        String text = contactUs.message();
        emailService.sendMailToAdmin(subject, text);
    }

    private void sendEmailToUser(ContactUsCreateDto contactUs) {
        String subject = "Thank you for contacting us";
        String text = "We will contact you as soon as possible.";
        emailService.sendSimpleMessage(contactUs.email(), subject, text);
    }
}
