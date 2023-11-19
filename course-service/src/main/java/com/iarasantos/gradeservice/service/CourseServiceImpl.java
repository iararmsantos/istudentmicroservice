package com.iarasantos.gradeservice.service;

import com.iarasantos.gradeservice.dto.CourseResponse;
import com.iarasantos.gradeservice.model.Course;
import com.iarasantos.gradeservice.model.Season;
import com.iarasantos.gradeservice.dto.CourseRequest;
import com.iarasantos.gradeservice.repository.CourseRepository;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    public Course createCourse(CourseRequest request) {
        Course course = Course.builder()
                .title(request.getTitle())
                .section(request.getSection())
                .year(request.getYear())
                .teacherId(request.getTeacherId())
                .build();
        course.setCreationDate(new Date());
        return repository.save(course);
    }

    @Override
    public List<CourseResponse> getCourses() {
        List<Course> courses = (List<Course>) repository.findAll();

        return courses.stream().map(course -> mapToCourseResponse(course)).toList();
    }

    private CourseResponse mapToCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .year(course.getYear())
                .section(course.getSection())
                .teacherId(course.getTeacherId())
                .creationDate(course.getCreationDate())
                .build();
    }

    @Override
    public Course getCourseById(long courseId) {
        return repository.findCourseById(courseId);
    }

    @Override
    public void deleteCourse(long courseId) {
        repository.deleteCourseById(courseId);
    }

    @Override
    public void updateCourse(Course course, CourseRequest request) {
        setCourseUpdate(course, request);
        repository.save(course);
    }

    private void setCourseUpdate(Course course, CourseRequest request) {
        if (request.getTitle() != null) {
            course.setTitle(request.getTitle());
        }

        if (request.getSection() != null && Arrays.stream(Season.values()).anyMatch(
                (t) -> t.name().equals(request.getSection()))) {
            course.setSection(request.getSection());
        }
    }
}
