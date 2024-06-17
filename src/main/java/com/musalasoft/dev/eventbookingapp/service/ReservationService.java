package com.musalasoft.dev.eventbookingapp.service;

import com.musalasoft.dev.eventbookingapp.dto.TicketRequest;
import com.musalasoft.dev.eventbookingapp.model.Reservation;

import java.util.List;

public interface ReservationService {

     Reservation saveReservation(Integer id, TicketRequest payload);

    List<Reservation> viewBookedEvents();

    void cancelReservations(Integer reservationId);
}
