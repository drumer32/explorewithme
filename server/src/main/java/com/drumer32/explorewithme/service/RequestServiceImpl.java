package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.State;
import com.drumer32.explorewithme.model.exception.AccessException;
import com.drumer32.explorewithme.model.exception.BadConditionException;
import com.drumer32.explorewithme.model.exception.ConflictException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.requests.ParticipationRequest;
import com.drumer32.explorewithme.model.requests.RequestStatus;
import com.drumer32.explorewithme.storage.RequestStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestStorage requestStorage;
    private final EventService eventService;

    private final UserService userService;

    @Override
    public List<ParticipationRequest> getParticipationRequestByEvent(Integer eventId, Integer userId) throws ObjectNotFoundException {
        log.info("Поиск заявки по id {} события", eventId);
        try {
            return requestStorage.getParticipationRequestByEvent(eventId);
        } catch (NullPointerException e) {
            throw new ObjectNotFoundException("Поиск заявки по id события не удался");
        }
    }

    @Override
    public ParticipationRequest getParticipationRequestByRequester(Integer id) {
        log.info("Поиск заявки по id {} заявителя", id);
        return requestStorage.getParticipationRequestByRequester(id);
    }

    @Override
    public ParticipationRequest get(Integer id) throws ObjectNotFoundException {
        log.info("Поиск заявки по id {}", id);
        try {
            return requestStorage.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Заявка не найдена");
        }
    }

    @Override
    public ParticipationRequest confirm(Integer eventId, Integer reqId, Integer userId)
            throws ObjectNotFoundException, AccessException {
        log.info("Запрос на подтверждение заявки {}", reqId);
        Event event = eventService.get(eventId);
        userService.get(userId);
        if (event.getConfirmedRequests() < event.getParticipantLimit()) {
            event.setConfirmedRequests(event.getConfirmedRequests()+1);
            if (event.getConfirmedRequests().equals(event.getParticipantLimit())) {
                for (ParticipationRequest cancelEvent : requestStorage.getParticipationRequestByEvent(eventId)) {
                    cancelEvent.setRequestStatus(RequestStatus.REJECTED);
                }
            }
            ParticipationRequest participationRequest = get(reqId);
            participationRequest.setRequestStatus(RequestStatus.CONFIRMED);
            return participationRequest;
        } else throw new AccessException("Достигнуто максимальное количество участников мероприятия");
    }

    @Override
    public ParticipationRequest reject(Integer eventId, Integer reqId, Integer userId) throws ObjectNotFoundException {
        log.info("Запрос на отклонение заявки {}", reqId);
        eventService.get(eventId);
        userService.get(userId);
        ParticipationRequest participationRequest = get(reqId);
        participationRequest.setRequestStatus(RequestStatus.REJECTED);
        return participationRequest;
    }

    @Override
    public ParticipationRequest createRequest(Integer userId, Integer eventId)
            throws ObjectNotFoundException, BadConditionException, ConflictException, AccessException {
        log.info("Запрос на создание заявки на событие {} от пользователя {}", eventId, userId);
        Event event = eventService.get(eventId);
        if (event.getInitiator().equals(userId)) {
            throw new ConflictException("Создатель мероприятия не может быть участником");
        } else if (!event.getState().equals(State.PUBLISHED)) {
            throw new BadConditionException("Событие уже опубликовано!");
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new AccessException("Количество подтвержденных участников - максимальное");
        }
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(userId);
        request.setCreated(LocalDateTime.now());
        request.setRequestStatus(RequestStatus.WAITING);
        request.setEvent(eventId);
        requestStorage.save(request);
        return request;
    }

    @Override
    public ParticipationRequest cancelRequest(Integer userId, Integer requestId) throws ObjectNotFoundException {
        log.info("Запрос на отклонение заявки {} от пользователя {}", requestId, userId);
        ParticipationRequest request = get(requestId);
        request.setRequestStatus(RequestStatus.REJECTED);
        return request;
    }
}
