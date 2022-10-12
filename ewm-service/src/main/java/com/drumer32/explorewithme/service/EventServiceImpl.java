package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.event.*;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.tools.EventDtoMapper;
import com.drumer32.explorewithme.tools.EventValidation;
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
    public Event addEvent(Event event, Integer eventId) {
        log.info("Запрос на создание события {}", eventId);
        return eventStorage.save(event);
    }

    @Override
    public Collection<Event> searchEvent(List<Integer> userId, List<State> state, List<Integer> categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        log.info("Запрос на получение событий {}", userId);
        Pageable customPageable = PageRequest.of(from, size);
        return new ArrayList<>(eventStorage.getEventsByInitiatorInAndEventDateBetweenAndStateInAndCategoryIn(
                userId, rangeStart, rangeEnd, state, categories, customPageable).getContent());
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
    public Event get(int id) throws ObjectNotFoundException {
        log.info("Запрос на получение события {}", id);
        return eventStorage.findById(id).orElseThrow(() -> new ObjectNotFoundException("Событие не найдено"));
    }

    @Override
    public List<Event> getEventsByCreator(Integer userId, Integer from, Integer size) {
        log.info("Запрос на получение событий автора {}", userId);
        Pageable pageable = PageRequest.of(from, size);
        return new ArrayList<>(eventStorage.getEventByInitiator(userId, pageable).getContent());
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
                .map(mapper::eventToShortEventDto)
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
