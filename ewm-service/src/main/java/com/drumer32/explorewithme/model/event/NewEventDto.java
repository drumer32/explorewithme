package com.drumer32.explorewithme.model.event;

import com.drumer32.explorewithme.tools.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NewEventDto {

    String annotation;
    Integer category;
    String description;
    LocalDateTime eventDate;
    Location location;
    boolean paid;
    Integer participantLimit;
    boolean requestModeration;
    String title;
}
