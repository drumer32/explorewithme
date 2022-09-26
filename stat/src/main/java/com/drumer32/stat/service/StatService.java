package com.drumer32.stat.service;

import com.drumer32.stat.StatStorage;
import com.drumer32.stat.model.EndpointHit;
import com.drumer32.stat.model.ViewStats;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatService {
    private final StatStorage statStorage;

    public void save(EndpointHit endpointHit) {
        statStorage.save(endpointHit);
    }

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        List<ViewStats> viewStats;
        if (!unique) {
            viewStats = statStorage.findAllNotUnique(start, end);
        } else {
            viewStats = statStorage.findAllUnique(start, end);
        }
        if (uris != null) {
            return viewStats.stream()
                    .map(view -> filterByUri(view, uris))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return viewStats;
        }
    }

    public ViewStats filterByUri(ViewStats viewStats, String[] uris) {
        for (String uri : uris) {
            if (viewStats.getUri().equals(uri)) {
                return viewStats;
            }
        }
        return null;
    }
}
