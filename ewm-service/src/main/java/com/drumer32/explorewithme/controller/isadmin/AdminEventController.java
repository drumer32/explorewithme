package com.drumer32.explorewithme.controller.isadmin;

import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.NewEventDto;
import com.drumer32.explorewithme.model.event.State;
import com.drumer32.explorewithme.model.exception.BadConditionException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventController {

    private final EventService eventService;

    @GetMapping
    public Collection<Event> getEvents(@RequestParam(required = false) List<Integer> userId,
                                       @RequestParam(required = false) List<State> state,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false) LocalDateTime rangeStart,
                                       @RequestParam(required = false) LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size) {
        return eventService.searchEvent(userId, state, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Integer eventId,
                             @RequestBody NewEventDto event) throws ObjectNotFoundException {
        return eventService.updateEvent(eventId, event);
    }

    @PatchMapping("/{eventId}/publish")
    public Event publishEvent(@PathVariable Integer eventId,
                              @RequestBody Event event) throws BadConditionException, ObjectNotFoundException {
        return eventService.addEvent(event, eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public Event rejectEvent(@PathVariable Integer eventId) throws ObjectNotFoundException {
        return eventService.rejectEvent(eventId);
    }
}
