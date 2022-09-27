package com.drumer32.explorewithme.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class WebClientConfig {

    @Value("${serviceUrl}")
    private String serviceUrl;

    @Value("${appName}")
    private String appName;

    @Bean
    public EventClient eventClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serviceUrl))
                .build();

        EventClient client = new EventClient(restTemplate);
        client.setAppName(appName);
        return client;
    }
}
