package com.musalasoft.dev.eventbookingapp.dto;

import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.model.User;

public record ReservationResponseDto(Integer reservationId, Integer attendeesCount, User user, Event event) {
}
