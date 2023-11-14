package com.iarasantos.gatewayservice.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    RestTemplate getTemplate() {
        var restTemplate = new RestTemplate();
        var httpClient = HttpClientBuilder.create().build();
        var requestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient) httpClient);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
