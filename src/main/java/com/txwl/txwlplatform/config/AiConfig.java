package com.txwl.txwlplatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AiConfig {

    @Value("${ai.api.timeout:30000}")
    private int timeout;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}