package com.iarasantos.gatewayservice.proxy;

import com.iarasantos.gatewayservice.dto.TeacherRequest;
import com.iarasantos.gatewayservice.modal.Teacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TeacherProxy {
    private final RestTemplate restTemplate;
    private final String url;

    public TeacherProxy(@Value("${teacher.url}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    public Teacher[] getTeachers() {
        return restTemplate.getForObject(url, Teacher[].class);
    }

    public void createTeacher(Teacher teacher) {
        restTemplate.postForObject(url, teacher, Teacher.class);
    }

    public void remove(String id) {
        restTemplate.delete(url + "/" + id);
    }

    public void update(String teacherId, HttpEntity<TeacherRequest> request) {
        restTemplate.patchForObject(url + "/" + teacherId, request, Void.class);
    }
}
