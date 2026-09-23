package com.eventify.repository;

import com.eventify.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // DERIVED QUERIES

    // Search for events by venue id
    List<Event> findByVenueId(Long venueId);

    // Search for events by venue name
    List<Event> findByVenueName(String venueName);

    // Search for events at a specific location by ID
    List<Event> findByEventDateAfter(LocalDate date);


}
