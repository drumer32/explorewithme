package com.drumer32.explorewithme.controller.isprivate;

import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.EventFullDto;
import com.drumer32.explorewithme.model.event.NewEventDto;
import com.drumer32.explorewithme.model.exception.AccessException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.exception.BadConditionException;
import com.drumer32.explorewithme.model.requests.ParticipationRequest;
import com.drumer32.explorewithme.tools.EventDtoMapper;
import com.drumer32.explorewithme.service.EventService;
import com.drumer32.explorewithme.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class PrivateEventController {

    private final EventService eventService;
    private final RequestService requestService;
    private final EventDtoMapper mapper;

    @GetMapping("/{userId}/events")
    public List<EventFullDto> getEvents(@PathVariable Integer userId,
                                   @RequestParam(defaultValue = "0") Integer from,
                                   @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getEventsByCreator(userId, from, size)
                .stream()
                .map(mapper::eventToFullDto)
                .toList();
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable Integer userId,
                                    @RequestBody NewEventDto newEventDto) throws ObjectNotFoundException {
        return mapper.eventToFullDto(eventService.updateEvent(userId, newEventDto));
    }

    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(@PathVariable Integer userId,
                                    @RequestBody NewEventDto newEventDto) throws BadConditionException, ObjectNotFoundException {
        if (newEventDto.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
            return mapper.eventToFullDto(eventService.addEvent(mapper.newEventDtoToEvent(userId ,newEventDto), userId));
        } else throw new BadConditionException("До начала мероприятия должно быть больше двух часов");
    }
    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventById(@PathVariable Integer eventId,
                                     @PathVariable Integer userId) throws ObjectNotFoundException {
        return mapper.eventToFullDto(eventService.get(eventId));
    }
    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Integer eventId,
                                    @PathVariable Integer userId) throws ObjectNotFoundException {
        Event event = eventService.rejectEvent(eventId);
        return mapper.eventToFullDto(event);
    }
    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequest> getParticipantRequests(@PathVariable Integer eventId,
                                                             @PathVariable Integer userId) throws ObjectNotFoundException {
        return requestService.getParticipationRequestByEvent(eventId, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequest approveRequest(@PathVariable Integer eventId,
                                               @PathVariable Integer reqId,
                                               @PathVariable Integer userId)
            throws ObjectNotFoundException, BadConditionException, AccessException {
        return requestService.confirm(eventId, reqId, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequest rejectRequest(@PathVariable Integer eventId,
                                              @PathVariable Integer reqId,
                                              @PathVariable Integer userId) throws ObjectNotFoundException {
        return requestService.reject(eventId, reqId, userId);
    }
}
