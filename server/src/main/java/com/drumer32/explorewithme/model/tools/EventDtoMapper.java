package com.drumer32.explorewithme.model.tools;

import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.EventFullDto;
import com.drumer32.explorewithme.model.event.EventShortDto;
import com.drumer32.explorewithme.model.event.NewEventDto;
import com.drumer32.explorewithme.model.user.UserShortDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventDtoMapper {

    private ModelMapperUtil mapper;

    public Event newEventDtoToEvent(Integer userId, NewEventDto newEventDto) {
        Event event = mapper.map(newEventDto, Event.class);
        event.setInitiator(userId);
        return event;
    }

    public EventFullDto eventToFullDto(Event event) {
        EventFullDto eventFullDto = mapper.map(event, EventFullDto.class);
        UserShortDto initiator = mapper.map(event.getInitiator(), UserShortDto.class);
        eventFullDto.setInitiator(initiator);
        return eventFullDto;
    }

    public EventShortDto eventToShortEventDto(Event event) {
        return mapper.map(event, EventShortDto.class);
    }
}
