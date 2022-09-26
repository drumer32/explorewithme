package com.drumer32.explorewithme.model.tools;

import com.drumer32.explorewithme.model.event.Event;

import java.time.LocalDateTime;
import java.util.List;

public class EventValidation {

    public static Event validateByText(Event event, String text) {
        String annotation = event.getAnnotation().toLowerCase();
        String description = event.getDescription().toLowerCase();
        if (text != null) {
            text = text.toLowerCase();
            if (annotation.contains(text) || description.contains(text)) {
                return event;
            } else {
                return null;
            }
        } else {
            return event;
        }
    }

    public static Event validateByEventDate(Event event, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        LocalDateTime eventDate = event.getEventDate();
        if (rangeStart != null && rangeEnd != null) {
            return (eventDate.isAfter(rangeStart) && eventDate.isBefore(rangeEnd)) ? event : null;
        } else if (rangeStart != null) {
            return eventDate.isAfter(rangeStart) ? event : null;
        } else if (rangeEnd != null) {
            return eventDate.isBefore(rangeEnd) ? event : null;
        } else {
            return event;
        }
    }

    public static Event validateByCategoryId(Event event, List<Integer> categories) {
        for (Integer catId : categories) {
            if (event.getCategory().equals(catId)) {
                return event;
            }
        }
        return null;
    }
}
