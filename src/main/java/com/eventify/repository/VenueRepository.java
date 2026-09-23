package com.eventify.repository;

import com.eventify.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    // DERIVED QUERIES

    // Search for a venue by name
    Optional<Venue> findByName(String name);

    // Search a venue that contains a specific word
    List<Venue> findByNameContaining(String keyword);

    // Search for a venue with the largest capacity
    List<Venue> findByCapacityGreaterThan(Integer capacity);
}
