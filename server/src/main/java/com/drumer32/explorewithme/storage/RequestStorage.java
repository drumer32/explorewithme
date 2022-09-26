package com.drumer32.explorewithme.storage;

import com.drumer32.explorewithme.model.requests.ParticipationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestStorage extends JpaRepository<ParticipationRequest, Integer> {
    List<ParticipationRequest> getParticipationRequestByEvent(Integer eventId);

    ParticipationRequest getParticipationRequestByRequester(Integer id);
}
