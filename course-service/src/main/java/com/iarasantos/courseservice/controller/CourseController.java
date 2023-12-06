package com.iarasantos.courseservice.controller;

import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService service;

    @GetMapping
    public List<CourseVO> getCourses() {
        return service.getCourses();
    }

    @PostMapping
    public CourseVO createCourse(@Valid @RequestBody CourseVO course) {
        return service.createCourse(course);
    }

    @GetMapping("/{id}")
    public CourseVO getCourse(@PathVariable("id") Long courseId) {
        return service.getCourseById(courseId);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Long courseId) {
        service.deleteCourse(courseId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CourseVO updateCourse(@RequestBody CourseVO request) {

        return service.updateCourse(request);
    }
}
