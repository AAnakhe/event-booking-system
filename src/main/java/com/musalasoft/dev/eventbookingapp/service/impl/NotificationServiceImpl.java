package com.musalasoft.dev.eventbookingapp.service.impl;

import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.model.User;
import com.musalasoft.dev.eventbookingapp.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendNotification(User user, Event event) {

        log.info("sendNotification triggered");

        String subject = "Reminder: Upcoming Event - " + event.getName();
        String message = String.format("Dear %s,\n\nYou have an upcoming event: %s\nDate: %s\nDescription: %s\n\nBest Regards,\nEvent Booking App",
                user.getName(), event.getName(), event.getDate(), event.getEventDescription());

        sendEmail(user.getEmail(), subject, message);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            mailSender.send(message);
            log.info("Notification email sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send notification email to {}", to, e);
        }

    }
}
