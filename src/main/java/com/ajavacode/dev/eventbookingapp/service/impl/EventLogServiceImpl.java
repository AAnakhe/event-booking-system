package com.ajavacode.dev.eventbookingapp.service.impl;

import com.ajavacode.dev.eventbookingapp.repository.EventLogRepository;
import com.ajavacode.dev.eventbookingapp.schedular.EventLog;
import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.model.User;
import com.ajavacode.dev.eventbookingapp.service.EventLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventLogServiceImpl implements EventLogService {

    private final EventLogRepository repository;

    @Override
    public void createEventLog(User user, Event event, String message) {
        EventLog eventLog = new EventLog();
        eventLog.setUser(user);
        eventLog.setEvent(event);
        eventLog.setMessage(message);
        eventLog.setTimestamp(LocalDateTime.now());
        repository.save(eventLog);
    }
}
