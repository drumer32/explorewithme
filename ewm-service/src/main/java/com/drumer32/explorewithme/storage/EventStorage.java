package com.drumer32.explorewithme.storage;

import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventStorage extends JpaRepository<Event, Integer> {

    List<Event> findAllByState(State state);

    Page<Event> getEventByInitiator(Integer userId, Pageable pageable);

    Page<Event> getEventsByInitiatorInAndEventDateBetweenAndStateInAndCategoryIn(List<Integer> userId, LocalDateTime start,
                                                                             LocalDateTime end, List<State> state,
                                                                             List<Integer> catId, Pageable pageable);

}
