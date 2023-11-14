package com.iarasantos.gatewayservice.proxy;

import com.iarasantos.gatewayservice.dto.StudentRequest;
import com.iarasantos.gatewayservice.modal.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StudentProxy {
    private final RestTemplate restTemplate;
    private final String url;

    public StudentProxy(@Value("${student.url}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = new  RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    public Student[] getStudents() {
        return restTemplate.getForObject(url, Student[].class);
    }

    public void createStudent(Student student) {
        restTemplate.postForObject(url, student, Student.class);
    }

    public void remove(String id) {
        restTemplate.delete(url + "/" + id);
    }

    public void update(String studentId, HttpEntity<StudentRequest> request) {
        restTemplate.patchForObject(url + "/" + studentId, request, Void.class);
    }
}
