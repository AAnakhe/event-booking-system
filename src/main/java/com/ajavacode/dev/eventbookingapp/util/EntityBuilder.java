package com.ajavacode.dev.eventbookingapp.util;

import com.ajavacode.dev.eventbookingapp.enums.Category;
import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.model.User;
import com.ajavacode.dev.eventbookingapp.dto.EventDto;
import com.ajavacode.dev.eventbookingapp.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class EntityBuilder {

    private final BCryptPasswordEncoder passwordEncoder;

    public User buildUser(UserDto payload) {
        User user = new User();
        user.setName(payload.name());
        user.setEmail(payload.email());
        user.setPassword(passwordEncoder.encode(payload.password()));
        return user;
    }

    public static Event buildEvent(EventDto payload){
        Event event = new Event();
        event.setName(payload.name());
        event.setDate(LocalDate.parse(payload.date(), DateTimeFormatter.ISO_LOCAL_DATE));
        event.setAvailableAttendeesCount(payload.availableAttendeesCount());
        event.setEventDescription(payload.description());
        event.setCategory(Category.valueOf(payload.category().toUpperCase()));
        return event;
    }
}
