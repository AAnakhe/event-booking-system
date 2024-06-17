package com.ajavacode.dev.eventbookingapp.repository;


import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.events e WHERE e = :event")
    List<User> findAttendeesByEvent(@Param("event") Event event);
}
