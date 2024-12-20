package com.iarasantos.courseservice.service;

import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface CourseService {
    CourseVO createCourse(CourseVO course);

    PagedModel<EntityModel<CourseVO>> getCourses(Pageable pageable);

    CourseVO getCourseById(long courseId);

    void deleteCourse(long courseId);

    CourseVO updateCourse(CourseVO request);

}
