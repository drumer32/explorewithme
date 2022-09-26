package com.drumer32.stat;

import com.drumer32.stat.model.EndpointHit;
import com.drumer32.stat.model.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StatStorage extends JpaRepository<EndpointHit, Integer> {

    String NON_UNIQUE = "select distinct(e.uri) as uri, count(e.app) as hits, e.app as app " +
            "from EndpointHit e where e.timestamp > ?1 and e.timestamp < ?2" +
            " group by e.app, (e.uri)";
    String UNIQUE = NON_UNIQUE + ", e.ip";

    @Query(value = NON_UNIQUE)
    List<ViewStats> findAllNotUnique(LocalDateTime start, LocalDateTime end);

    @Query(value = UNIQUE)
    List<ViewStats> findAllUnique(LocalDateTime start, LocalDateTime end);
}
