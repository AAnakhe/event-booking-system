package com.ajavacode.dev.eventbookingapp.schedular;

import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.model.User;
import com.ajavacode.dev.eventbookingapp.service.EventLogService;
import com.ajavacode.dev.eventbookingapp.service.EventService;
import com.ajavacode.dev.eventbookingapp.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yaml")
public class EventNotificationSchedulerTest {

    @MockBean
    private EventService eventService;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private EventLogService eventLogService;

    @Autowired
    private EventNotificationScheduler eventNotificationScheduler;

    @MockBean
    private Event event;

    @MockBean
    private User user;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1);
        event.setName("Test Event");
        event.setDate(LocalDate.now().plusDays(1));
        event.setEventDescription("Test Event Description");

        user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("testuser@example.com");
    }

    @Test
    void testSendUpcomingEventNotifications() {
        Mockito.when(eventService.findEventsByDate(LocalDate.now().plusDays(1)))
                .thenReturn(Collections.singletonList(event));
        Mockito.when(eventService.findAttendeesByEvent(event))
                .thenReturn(Collections.singletonList(user));

        eventNotificationScheduler.sendUpcomingEventNotifications();

        verify(notificationService).sendNotification(user, event);
        verify(eventLogService, atLeastOnce()).createEventLog(user, event, "Notification sent for upcoming event");
    }
}
