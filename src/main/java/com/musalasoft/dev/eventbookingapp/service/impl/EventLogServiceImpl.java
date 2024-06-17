package com.musalasoft.dev.eventbookingapp.service.impl;

import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.model.User;
import com.musalasoft.dev.eventbookingapp.repository.EventLogRepository;
import com.musalasoft.dev.eventbookingapp.schedular.EventLog;
import com.musalasoft.dev.eventbookingapp.service.EventLogService;
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
