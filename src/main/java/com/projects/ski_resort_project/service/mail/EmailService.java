package com.projects.ski_resort_project.service.mail;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendMailToAdmin(String subject, String text);
}
