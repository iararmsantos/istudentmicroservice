package com.iarasantos.courseservice.controller;

import com.iarasantos.common.utilcommon.util.MediaType;
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

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public List<CourseVO> getCourses() {
        return service.getCourses();
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public CourseVO createCourse(@Valid @RequestBody CourseVO course) {
        return service.createCourse(course);
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public CourseVO getCourse(@PathVariable("id") Long courseId) {
        return service.getCourseById(courseId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("id") Long courseId) {
        service.deleteCourse(courseId);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @ResponseStatus(HttpStatus.OK)
    public CourseVO updateCourse(@RequestBody CourseVO request) {

        return service.updateCourse(request);
    }
}
