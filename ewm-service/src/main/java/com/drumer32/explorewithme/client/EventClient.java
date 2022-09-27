package com.drumer32.explorewithme.client;

import lombok.Setter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

public class EventClient extends BaseClient {

    @Setter
    private String appName;

    public EventClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public void addRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        post("/hit", new EndpointHit(appName, uri, ip));
    }
}
