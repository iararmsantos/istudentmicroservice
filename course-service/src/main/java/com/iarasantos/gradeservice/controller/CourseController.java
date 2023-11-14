package com.iarasantos.gradeservice.controller;

import com.iarasantos.gradeservice.dto.CourseResponse;
import com.iarasantos.gradeservice.model.Course;
import com.iarasantos.gradeservice.dto.CourseRequest;
import com.iarasantos.gradeservice.service.CourseService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService service;

    @GetMapping
    public List<CourseResponse> getCourses() {
        return service.getCourses();
    }

    @PostMapping
    public void createCourse(@Valid @RequestBody CourseRequest course) {
        service.createCourse(course);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") String courseId) {
        return Optional.ofNullable(courseId)
                .map(c -> Long.valueOf(courseId))
                .map(service::getCourseById)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") String courseId) {
        Course course = Optional.ofNullable(courseId)
                .map(c -> Long.valueOf(courseId))
                .map(service::getCourseById)
                .orElseThrow();
        service.deleteCourse(course.getId());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCourse(@PathVariable("id") String courseId, @RequestBody CourseRequest request) {
        validateForUpdate(request);
        Course course = Optional.ofNullable(courseId)
                .map(c -> Long.valueOf(courseId))
                .map(service::getCourseById)
                .orElseThrow();
        service.updateCourse(course, request);
    }

    private void validateForUpdate(CourseRequest request) {
        if (request.getTitle() != null && request.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }

    }
}
