package com.eventify.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private Long venueId;
}
