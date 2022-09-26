package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.EventShortDto;
import com.drumer32.explorewithme.model.event.NewEventDto;
import com.drumer32.explorewithme.model.event.State;
import com.drumer32.explorewithme.model.exception.BadConditionException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public interface EventService {

    Event addEvent(Event event) throws BadConditionException;
    Collection<Event> searchEvent(List<Integer> userId, List<State> state, List<Integer> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);
    Event updateEvent(Integer id, NewEventDto event) throws ObjectNotFoundException;
    Event rejectEvent(Integer id) throws ObjectNotFoundException;
    Event get(Integer id) throws ObjectNotFoundException;
    List<Integer> getEventsByCreator(Integer userId, Integer from, Integer size);

    List<EventShortDto> getEventsForPublic(String text, List<Integer> categories, Boolean paid,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
                                           String sort, Integer from, Integer size);
}
