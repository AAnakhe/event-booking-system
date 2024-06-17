package com.ajavacode.dev.eventbookingapp.schedular;

import com.ajavacode.dev.eventbookingapp.service.EventLogService;
import com.ajavacode.dev.eventbookingapp.service.EventService;
import com.ajavacode.dev.eventbookingapp.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.AbstractMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventNotificationScheduler {

    private final EventService eventService;

    private final NotificationService notificationService;

    private final EventLogService eventLogService;

    @Scheduled(cron = "0 30 10  * * ?")
    public void sendUpcomingEventNotifications() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        eventService.findEventsByDate(tomorrow).stream()
                .flatMap(event -> eventService.findAttendeesByEvent(event).stream()
                        .map(user ->
                                new
                                        AbstractMap.SimpleEntry<>(user, event)))
                .forEach(entry -> {
                    notificationService.sendNotification(entry.getKey(), entry.getValue());
                    eventLogService.createEventLog(entry.getKey(), entry.getValue(),
                            "Notification sent for upcoming event"
                    );
                });
    }
}
