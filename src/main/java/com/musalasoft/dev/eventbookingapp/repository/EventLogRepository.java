package com.musalasoft.dev.eventbookingapp.repository;

import com.musalasoft.dev.eventbookingapp.schedular.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Integer> {
}
