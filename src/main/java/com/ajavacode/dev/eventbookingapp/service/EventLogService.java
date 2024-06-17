package com.ajavacode.dev.eventbookingapp.service;

import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.model.User;

public interface EventLogService {

    void createEventLog(User user, Event event, String message);
}
