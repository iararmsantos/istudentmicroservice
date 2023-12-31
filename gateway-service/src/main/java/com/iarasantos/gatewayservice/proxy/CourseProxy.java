package com.iarasantos.gatewayservice.proxy;

import com.iarasantos.gatewayservice.modal.Course;
import com.iarasantos.gatewayservice.dto.CourseRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CourseProxy {
    private final RestTemplate restTemplate;

    @Value("${urls.courses}")//create this in all proxyes
    private String url;

    public CourseProxy(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    public Course[] getCourses() {
        return restTemplate.getForObject(url, Course[].class);
    }

    public void createCourse(Course course) {
        restTemplate.postForObject(url, course, Course.class);
    }

    public void remove(String id) {
        restTemplate.delete(url + "/" + id);
    }

    public void update(String courseId, CourseRequest body) {
        restTemplate.patchForObject(url + "/" + courseId, body, Void.class);
    }
}
