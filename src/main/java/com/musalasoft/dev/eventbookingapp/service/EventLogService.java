package com.musalasoft.dev.eventbookingapp.service;

import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.model.User;

public interface EventLogService {

    void createEventLog(User user, Event event, String message);
}
