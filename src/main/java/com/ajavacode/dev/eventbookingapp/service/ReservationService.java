package com.ajavacode.dev.eventbookingapp.service;

import com.ajavacode.dev.eventbookingapp.dto.TicketRequest;
import com.ajavacode.dev.eventbookingapp.model.Reservation;

import java.util.List;

public interface ReservationService {

     Reservation saveReservation(Integer id, TicketRequest payload);

    List<Reservation> viewBookedEvents();

    void cancelReservations(Integer reservationId);
}
