package com.musalasoft.dev.eventbookingapp.service;

import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.model.User;

public interface NotificationService {
    void sendNotification(User user, Event event);
}
