package com.eventify.service;


import com.eventify.dto.VenueDTO;
import com.eventify.exception.ResourceNotFoundException;
import com.eventify.model.Venue;
import com.eventify.repository.VenueRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    // Inyección de dependencias por constructor
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    // 1. CREAR (POST)
    @Transactional
    public VenueDTO createVenue(VenueDTO requestDTO) {
        Venue venue = new Venue();
        venue.setName(requestDTO.getName());
        venue.setAddress(requestDTO.getAddress());
        venue.setCapacity(requestDTO.getCapacity());

        Venue savedVenue = venueRepository.save(venue);
        return mapToResponseDTO(savedVenue);
    }

    // 2. CONSULTAR POR ID (GET)
    @Transactional(readOnly = true)
    public VenueDTO getVenueById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el lugar (Venue) con ID: " + id));
        return mapToResponseDTO(venue);
    }

    // 3. CONSULTAR TODOS CON PAGINACIÓN (GET) - TASK 3
    @Transactional(readOnly = true)
    public Page<VenueDTO> getAllVenues(Pageable pageable) {
        return venueRepository.findAll(pageable).map(this::mapToResponseDTO);
    }

    // 4. ACTUALIZAR (PUT) - TASK 2
    @Transactional
    public VenueDTO updateVenue(Long id, VenueDTO requestDTO) {
        Venue existingVenue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. No existe el lugar (Venue) con ID: " + id));

        existingVenue.setName(requestDTO.getName());
        existingVenue.setAddress(requestDTO.getAddress());
        existingVenue.setCapacity(requestDTO.getCapacity());

        Venue updatedVenue = venueRepository.save(existingVenue);
        return mapToResponseDTO(updatedVenue);
    }

    // 5. ELIMINAR (DELETE) - TASK 2
    @Transactional
    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. No existe el lugar (Venue) con ID: " + id);
        }
        venueRepository.deleteById(id);
    }

    // Método privado para mapear Entidad -> DTO de respuesta
    private VenueDTO mapToResponseDTO(Venue venue) {
        return new VenueDTO(
                venue.getId(),
                venue.getName(),
                venue.getAddress(),
                venue.getCapacity()
        );
    }

}
