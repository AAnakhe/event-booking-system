package com.ajavacode.dev.eventbookingapp.service.impl;

import com.ajavacode.dev.eventbookingapp.exceptions.EventCreationException;
import com.ajavacode.dev.eventbookingapp.exceptions.ResourceNotFoundException;
import com.ajavacode.dev.eventbookingapp.repository.EventRepository;
import com.ajavacode.dev.eventbookingapp.repository.UserRepository;
import com.ajavacode.dev.eventbookingapp.dto.EventDto;
import com.ajavacode.dev.eventbookingapp.enums.Category;
import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.model.User;
import com.ajavacode.dev.eventbookingapp.service.EventService;
import com.ajavacode.dev.eventbookingapp.util.EntityBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Event createEvent(EventDto payload) {
        try {
            Event event = EntityBuilder.buildEvent(payload);
            return eventRepository.save(event);
        } catch (Exception e) {
            log.error("Failed to create event {}", e.getMessage());
            throw new EventCreationException("Failed to create event " + e.getMessage());
        }
    }

    @Override
    public List<Event> searchEvent(String name, String startDate, String endDate, String category) {
        try {
            if (name != null) {
                return eventRepository.findByNameContaining(name);
            }
            if (startDate != null && endDate != null) {
                return eventRepository.findByDateBetween(LocalDate.parse(startDate), LocalDate.parse(endDate));
            }
            if (category != null) {
                return eventRepository.findByCategory(Category.valueOf(category.toUpperCase()));
            } else {
                return eventRepository.findAll();
            }
        } catch (Exception e) {
            log.error("Failed to fetch events: {}", e.getMessage());
            throw new ResourceNotFoundException("Failed to fetch events" + e.getMessage());
        }
    }

    @Override
    public Event findEventById(Integer id) {
        try{
            return eventRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Failed to fetch event with id: {} {}", id, e.getMessage());
            throw new ResourceNotFoundException("Failed to fetch event with id: " + id + e);
        }
    }

    @Override
    public void save(Event event) {
        try {
            eventRepository.save(event);
        }catch (Exception e){
            log.error("Failed to save event {}", e.getMessage());
            throw new EventCreationException("Failed to save event " + e);
        }
    }

    @Override
    public List<Event> findEventsByDate(LocalDate date) {
       return Optional.ofNullable(eventRepository.findEventsByDate(date))
               .orElseThrow(() -> new ResourceNotFoundException("no events found with date:" + date));
    }

    @Override
    public List<User> findAttendeesByEvent(Event event) {
        return Optional.ofNullable(userRepository.findAttendeesByEvent(event))
                .orElseThrow(() -> new ResourceNotFoundException("No attendees found for event: " + event.getName()));
    }

}
