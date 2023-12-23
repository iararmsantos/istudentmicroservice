package com.iarasantos.courseservice.mapper.mocks;

import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.model.Course;
import com.iarasantos.courseservice.model.Season;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockCourse {

    public Course mockEntity() {
        return mockEntity(0);
    }

    public CourseVO mockVO() {
        return mockVO(0);
    }

    public List<CourseVO> mockVOList() {
        List<CourseVO> courses = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            courses.add(mockVO(i));
        }
        return courses;
    }

    public List<Course> mockEntityList() {
        List<Course> courses = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            courses.add(mockEntity(i));
        }
        return courses;
    }

    public Course mockEntity(Integer number) {
        Course course = new Course();
        course.setId(Long.valueOf(number));
        course.setTitle("Title Test" + number);
        course.setSection(Season.FALL);
        course.setYear(number);
        course.setTeacherId(Long.valueOf(number));

        course.setCreationDate(new Date());

        return course;
    }

    public CourseVO mockVO(Integer number) {
        CourseVO course = new CourseVO();
        course.setKey(Long.valueOf(number));
        course.setTitle("Title Test" + number);
        course.setSection(Season.FALL);
        course.setYear(number);
        course.setTeacherId(Long.valueOf(number));

        course.setCreationDate(new Date());

        return course;
    }
}
