package com.eventify.service;

import com.eventify.exception.ResourceNotFoundException;
import com.eventify.dto.EventDTO;
import com.eventify.model.*;
import com.eventify.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    // 1. CREAR (POST)
    public EventDTO createEvent(EventDTO eventDTO) {

        Venue venue = venueRepository.findById(eventDTO.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Venue no encontrado con id: " + eventDTO.getVenueId()
                ));

        Event event = new Event();

        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setEventDate(eventDTO.getEventDate());
        event.setVenue(venue);

        Event savedEvent = eventRepository.save(event);
        return mapToResponseDTO(savedEvent);
    }

    // 2. CONSULTAR POR ID (GET)
    @Transactional(readOnly = true)
    public EventDTO getEventById(Long id){
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el lugar (Venue) con ID: " + id));
        return mapToResponseDTO(event);
    }

    // 3. CONSULTAR TODOS CON PAGINACIÓN (GET)
    @Transactional(readOnly = true)
    public Page<EventDTO> geTAllEvents(Pageable  pageable){
        return eventRepository.findAll(pageable).map(this::mapToResponseDTO);
    }

    // 4. ACTUALIZAR (PUT)
    @Transactional
    public EventDTO updateEvent(Long id, EventDTO requestDTO) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se puede actualizar. No existe el evento (Event) con ID: " + id)
        );

        Venue venue = venueRepository.findById(requestDTO.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Venue no encontrado con id: " + requestDTO.getVenueId()
                ));

        existingEvent.setName(requestDTO.getName());
        existingEvent.setDescription(requestDTO.getDescription());
        existingEvent.setEventDate(requestDTO.getEventDate());
        existingEvent.setVenue(venue);

        Event updateEvent = eventRepository.save(existingEvent);
        return mapToResponseDTO(updateEvent);
    }

    // 5. ELIMINAR (DELETE)
    @Transactional
    public void deleteEvent(Long id) {
        if(!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. No existe el lugar (Venue) con ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    private EventDTO mapToResponseDTO(Event event){
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getEventDate(),
                event.getVenue().getId()
        );
    }
}
