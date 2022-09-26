package com.drumer32.explorewithme.storage;

import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStorage extends JpaRepository<Event, Integer> {

    Page<Event> getEventByInitiator(Integer initiator, Pageable pageable);
    List<Event> findAllByState(State state);
}
