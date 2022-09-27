package com.drumer32.explorewithme.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;

@Service
public class EventClient extends BaseClient {

    @Value("${appName}")
    private String appName;

    @Autowired
    public EventClient(@Value("${serviceUrl}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .build()
        );
    }

    public void addRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        post("/hit", new EndpointHit(appName, uri, ip));
    }
}
