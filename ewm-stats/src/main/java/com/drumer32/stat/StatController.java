package com.drumer32.stat;

import com.drumer32.stat.model.EndpointHit;
import com.drumer32.stat.model.ViewStats;
import com.drumer32.stat.service.StatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class StatController {

    private final StatService service;

    @PostMapping("/hit")
    public void save(@RequestBody EndpointHit endpointHit) {
        service.save(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                    @RequestParam(required = false) String[] uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}
