package com.ajavacode.dev.eventbookingapp.service;

import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.model.User;

public interface NotificationService {
    void sendNotification(User user, Event event);
}
