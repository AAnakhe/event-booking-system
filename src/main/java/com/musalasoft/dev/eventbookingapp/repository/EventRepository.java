package com.musalasoft.dev.eventbookingapp.repository;

import com.musalasoft.dev.eventbookingapp.enums.Category;
import com.musalasoft.dev.eventbookingapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByNameContaining(String name);
    List<Event> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Event> findByCategory(Category category);
    List<Event> findEventsByDate(LocalDate date);
}
