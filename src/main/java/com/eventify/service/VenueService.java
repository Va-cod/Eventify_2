package com.eventify.service;


import com.eventify.dto.VenueRequestDTO;
import com.eventify.model.Venue;
import com.eventify.repository.VenueRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<Venue> display(){
        return venueRepository.findAll();
    }

    public Optional<Venue> getByID (Long id){
        return venueRepository.findById(id);
    }

    public Optional<Venue> update (Long id, VenueRequestDTO details) {
        return venueRepository.findById(id).map(venue -> {
            venue.setName(details.getName());
            venue.setAddress(details.getAddress());
            venue.setCapacity((details.getCapacity()));
            return venueRepository.save(venue);
        });
    }

    
}
