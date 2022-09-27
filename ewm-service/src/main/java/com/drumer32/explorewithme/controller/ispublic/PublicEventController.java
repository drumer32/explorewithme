package com.drumer32.explorewithme.controller.ispublic;

import com.drumer32.explorewithme.client.EventClient;
import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.EventShortDto;
import com.drumer32.explorewithme.model.event.State;
import com.drumer32.explorewithme.model.exception.AccessException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.tools.ModelMapperConfig;
import com.drumer32.explorewithme.service.EventService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@AllArgsConstructor
public class PublicEventController {

    private final EventService eventService;
    private final ModelMapper mapper;
    private final EventClient client;

    @GetMapping
    public List<EventShortDto> getPublishedEvents(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Integer> categoryId,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false)LocalDateTime rangeStart,
                                                  @RequestParam(required = false)LocalDateTime rangeEnd,
                                                  @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                                  @RequestParam(required = false) String sort,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                  @Positive @RequestParam(defaultValue = "10") int size,
                                                  HttpServletRequest request) {
        client.addRequest(request);
        return eventService.getEventsForPublic(text, categoryId, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size);
     }

    @GetMapping("/{id}")
    public EventShortDto getEventById(@PathVariable Integer id, HttpServletRequest request)
            throws ObjectNotFoundException, AccessException {
        client.addRequest(request);
        Event event = eventService.get(id);
        if (event.getState().equals(State.PUBLISHED)) {
            return mapper.map(event, EventShortDto.class);
        } else throw new AccessException("Данная опция доступна зарегистрированным пользователям и администратору");
    }
}
