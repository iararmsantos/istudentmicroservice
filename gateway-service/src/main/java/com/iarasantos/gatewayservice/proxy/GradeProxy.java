package com.iarasantos.gatewayservice.proxy;


import com.iarasantos.gatewayservice.modal.Grade;
import com.iarasantos.gatewayservice.dto.GradeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GradeProxy {
    private final RestTemplate restTemplate;
    @Value("${urls.grades}")//create this in all proxyes
    private String url;

    public GradeProxy(RestTemplate restTemplate) {
        this.restTemplate = new  RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    public Grade[] getGrades() {
        return restTemplate.getForObject(url, Grade[].class);
    }

    public void createGrade(Grade grade) {
        restTemplate.postForObject(url, grade, Grade.class);
    }

    public void remove(String id) {
        restTemplate.delete(url + "/" + id);
    }

    public void update(String gradeId, GradeRequest request) {
        restTemplate.patchForObject(url + "/" + gradeId, request, Void.class);
    }
}
