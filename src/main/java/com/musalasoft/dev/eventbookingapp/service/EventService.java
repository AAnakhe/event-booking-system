package com.musalasoft.dev.eventbookingapp.service;

import com.musalasoft.dev.eventbookingapp.dto.EventDto;
import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.model.User;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    Event createEvent(EventDto payload);
    List<Event> searchEvent(String name, String startDate, String endDate, String category);

    Event findEventById(Integer id);

    void save(Event event);

    List<Event> findEventsByDate(LocalDate date);
    List<User> findAttendeesByEvent(Event event);
}
