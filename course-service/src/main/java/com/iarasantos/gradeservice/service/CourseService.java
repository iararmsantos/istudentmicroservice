package com.iarasantos.gradeservice.service;

import com.iarasantos.gradeservice.dto.CourseResponse;
import com.iarasantos.gradeservice.model.Course;
import com.iarasantos.gradeservice.dto.CourseRequest;
import java.util.List;

public interface CourseService {
    public Course createCourse(CourseRequest course);
    public List<CourseResponse> getCourses();
    public Course getCourseById(long courseId);

    public void deleteCourse(long courseId);
    public void updateCourse(Course course, CourseRequest request);

}
