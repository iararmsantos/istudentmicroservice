package com.iarasantos.gatewayservice.controller;

import com.iarasantos.gatewayservice.modal.Course;
import com.iarasantos.gatewayservice.dto.CourseRequest;
import com.iarasantos.gatewayservice.proxy.CourseProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class GatewayCourseController {
    @Autowired
    private CourseProxy courseProxy;

    @GetMapping
    public Course[] getCourses() {
        return courseProxy.getCourses();
    }

    @PostMapping
    public void create(@RequestBody Course course) {
        courseProxy.createCourse(course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        courseProxy.remove(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") String courseId, @RequestBody CourseRequest request) {
        courseProxy.update(courseId, new HttpEntity<CourseRequest>(request).getBody());
    }
}
