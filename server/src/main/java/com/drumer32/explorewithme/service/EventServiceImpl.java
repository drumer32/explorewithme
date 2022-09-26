package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.EventShortDto;
import com.drumer32.explorewithme.model.event.NewEventDto;
import com.drumer32.explorewithme.model.event.State;
import com.drumer32.explorewithme.model.exception.BadConditionException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.tools.EventDtoMapper;
import com.drumer32.explorewithme.model.tools.EventValidation;
import com.drumer32.explorewithme.storage.EventStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private EventStorage eventStorage;

    private EventDtoMapper mapper;

    @Override
    public Event addEvent(Event event) throws BadConditionException {
        log.info("Запрос на создание события {}", event.getId());
        if (event.getState().equals(State.PENDING)) {
            return eventStorage.save(event);
        }
        else throw new BadConditionException("Статус должен быть PENDING");
    }

    @Override
    public Collection<Event> searchEvent(List<Integer> userId, List<State> state, List<Integer> categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        log.info("Запрос на получение событий {}", userId);
        Pageable customPageable = PageRequest.of(from, size);
        ArrayList<Event> events =  new ArrayList<>();
        for (Integer id : userId) {
            events.addAll(eventStorage.getEventByInitiator(id, customPageable).getContent());
        }
        events.removeIf(event -> (!state.contains(event.getState())) & (!categories.contains(event.getCategory())) &
                (!rangeStart.isBefore(event.getEventDate())) & (!rangeEnd.isAfter(event.getEventDate())));
        return events;
    }

    @Override
    public Event updateEvent(Integer id, NewEventDto newEvent) throws ObjectNotFoundException {
        log.info("Запрос на обновление информации о событии {}", newEvent.getTitle());
        Event oldEvent = get(id);
        oldEvent.setAnnotation(newEvent.getAnnotation());
        oldEvent.setDescription(newEvent.getDescription());
        oldEvent.setEventDate(newEvent.getEventDate());
        oldEvent.getLocation().setLon(newEvent.getLocation().getLon());
        oldEvent.getLocation().setLat(newEvent.getLocation().getLat());
        oldEvent.setPaid(newEvent.isPaid());
        oldEvent.setParticipantLimit(newEvent.getParticipantLimit());
        oldEvent.setRequestModeration(newEvent.isRequestModeration());
        oldEvent.setTitle(newEvent.getTitle());
        return oldEvent;
    }

    @Override
    public Event rejectEvent(Integer id) throws ObjectNotFoundException {
        log.info("Отклонить событие {}. Статус {}.", id, State.CANCELED);
        Event event = get(id);
        event.setState(State.CANCELED);
        return event;
    }

    @Override
    public Event get(Integer id) throws ObjectNotFoundException {
        log.info("Запрос на получение события {}", id);
        try {
            return eventStorage.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Событие не найдено");
        }
    }

    @Override
    public List<Integer> getEventsByCreator(Integer userId, Integer from, Integer size) {
        log.info("Запрос на получение событий автора {}", userId);
        List<Integer> result = new ArrayList<>();
        Pageable pageable = PageRequest.of(from, size);
        for (Event event : eventStorage.getEventByInitiator(userId, pageable).getContent()) {
            result.add(event.getId());
        }
        return result;
    }

    @Override
    public List<EventShortDto> getEventsForPublic(String text, List<Integer> categories, Boolean paid,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
                                                  String sort, Integer from, Integer size) {
        log.info("Запрос на получение опубликованных событий");
        List<Event> events = eventStorage.findAllByState(State.PUBLISHED);

        List<EventShortDto> list = events.stream()
                .map(event -> EventValidation.validateByEventDate(event, rangeStart, rangeEnd))
                .filter(Objects::nonNull)
                .map(event -> EventValidation.validateByCategoryId(event, categories))
                .filter(Objects::nonNull)
                .map(event -> EventValidation.validateByText(event, text))
                .filter(Objects::nonNull)
                .map(event -> mapper.eventToShortEventDto(event))
                .sorted((s1, s2) -> {
                    if (sort != null) {
                        return switch (sort) {
                        case "EVENT_DATE" -> s1.getEventDate().compareTo(s2.getEventDate());
                        case "VIEWS" -> s1.getViews().compareTo(s2.getViews());
                        default -> s1.getId().compareTo(s2.getId());
                    };
                        } else {
                            return s1.getId().compareTo(s2.getId());
                        }
                })
                .toList();

        PagedListHolder<EventShortDto> page = new PagedListHolder<>(list);
        page.setPageSize(size);
        page.setPage(from);
        return page.getPageList();
    }
}
