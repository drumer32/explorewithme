package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.exception.AccessException;
import com.drumer32.explorewithme.model.exception.BadConditionException;
import com.drumer32.explorewithme.model.exception.ConflictException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.requests.ParticipationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestService {
    List<ParticipationRequest> getParticipationRequestByEvent(Integer eventId, Integer userId) throws ObjectNotFoundException;

    ParticipationRequest getParticipationRequestByRequester(Integer id);
    ParticipationRequest get(Integer id) throws ObjectNotFoundException;

    ParticipationRequest confirm(Integer eventId, Integer reqId, Integer userId) throws ObjectNotFoundException, BadConditionException, AccessException;

    ParticipationRequest reject(Integer eventId, Integer reqId, Integer userId) throws ObjectNotFoundException;

    ParticipationRequest createRequest(Integer userId, Integer eventId) throws ObjectNotFoundException, BadConditionException, ConflictException, AccessException;

    ParticipationRequest cancelRequest(Integer userId, Integer requestId) throws ObjectNotFoundException;

}
