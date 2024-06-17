package com.musalasoft.dev.eventbookingapp.service.impl;

import com.musalasoft.dev.eventbookingapp.dto.TicketRequest;
import com.musalasoft.dev.eventbookingapp.exceptions.EventCreationException;
import com.musalasoft.dev.eventbookingapp.exceptions.ResourceNotFoundException;
import com.musalasoft.dev.eventbookingapp.exceptions.UserNotFoundException;
import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.model.Reservation;
import com.musalasoft.dev.eventbookingapp.model.User;
import com.musalasoft.dev.eventbookingapp.repository.ReservationRepository;
import com.musalasoft.dev.eventbookingapp.service.EventService;
import com.musalasoft.dev.eventbookingapp.service.ReservationService;
import com.musalasoft.dev.eventbookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final UserService userService;
    private final EventService eventService;
    private final ReservationRepository repository;

    @Override
    public Reservation saveReservation(Integer eventId, TicketRequest ticketRequest) {
        try {
            Event event = eventService.findEventById(eventId);
            User user = getCurrentUser().orElseThrow(() -> new UserNotFoundException("User not found"));

            if (event.getAvailableAttendeesCount() < ticketRequest.attendeesCount()) {
                throw new EventCreationException("Not enough available seats.");
            }

            event.setAvailableAttendeesCount(event.getAvailableAttendeesCount() - ticketRequest.attendeesCount());
            eventService.save(event);
            Reservation reservation = new Reservation();
            reservation.setEvent(event);
            reservation.setUser(user);
            reservation.setAttendeesCount(ticketRequest.attendeesCount());
            repository.save(reservation);

            event.getAttendees().add(user);
            eventService.save(event);

            return reservation;

        } catch (Exception e){
            log.error("Failed to create reservation: {}", e.getMessage());
            throw new EventCreationException("Failed to create reservation: " + e);
        }
    }

    @Override
    public List<Reservation> viewBookedEvents() {
        User user = getCurrentUser().orElseThrow(() -> new UserNotFoundException("User not found"));
        return repository.findByUser(user);
    }

    @Override
    public void cancelReservations(Integer reservationId) {
        User user = getCurrentUser().orElseThrow(() -> new UserNotFoundException("User not found"));
        Reservation reservation = Optional.ofNullable(repository.findByIdAndUser(reservationId, user))
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        Event event = reservation.getEvent();
        event.setAvailableAttendeesCount(event.getAvailableAttendeesCount() + reservation.getAttendeesCount());
        eventService.save(event);
        repository.delete(reservation);
    }
    public Optional<User> getCurrentUser(){
        try {
            return Optional.ofNullable(userService.findUserByEmail(SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName()));
        } catch (Exception e) {
            log.error("User not found");
           return Optional.empty();
        }
    }
}
