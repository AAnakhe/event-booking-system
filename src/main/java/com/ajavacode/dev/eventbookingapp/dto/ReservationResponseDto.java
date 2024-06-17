package com.ajavacode.dev.eventbookingapp.dto;

import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.model.User;

public record ReservationResponseDto(Integer reservationId, Integer attendeesCount, User user, Event event) {
}
