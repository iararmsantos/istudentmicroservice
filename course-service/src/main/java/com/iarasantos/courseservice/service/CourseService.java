package com.iarasantos.courseservice.service;

import com.iarasantos.courseservice.data.vo.v1.CourseVO;

import java.util.List;

public interface CourseService {
    public CourseVO createCourse(CourseVO course);

    public List<CourseVO> getCourses();

    public CourseVO getCourseById(long courseId);

    public void deleteCourse(long courseId);

    public CourseVO updateCourse(CourseVO request);

}
