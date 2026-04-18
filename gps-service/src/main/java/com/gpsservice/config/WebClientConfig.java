package com.gpsservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.device.service}")
    private String deviceService;

    @Bean
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl(deviceService)
                .build();
    }
}
