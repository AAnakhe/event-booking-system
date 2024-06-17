package com.musalasoft.dev.eventbookingapp.repository;

import com.musalasoft.dev.eventbookingapp.model.Reservation;
import com.musalasoft.dev.eventbookingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<List<Reservation>> findByUserId(Integer userId);

    List<Reservation> findByUser(User user);

    Reservation findByIdAndUser(Integer reservationId, User user);
}
