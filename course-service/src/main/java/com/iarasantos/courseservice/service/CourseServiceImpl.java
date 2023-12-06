package com.iarasantos.courseservice.service;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.exception.ResourceNotFoundException;
import com.iarasantos.courseservice.repository.CourseRepository;
import com.iarasantos.courseservice.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    public CourseVO createCourse(CourseVO request) {
        Course course = DozerMapper.parseObject(request, Course.class);
        course.setCreationDate(new Date());
        return DozerMapper.parseObject(repository.save(course), CourseVO.class);
    }

    @Override
    public List<CourseVO> getCourses() {
        return DozerMapper.parseListObjects(repository.findAll(), CourseVO.class);
    }

    @Override
    public CourseVO getCourseById(long courseId) {
        Course course = repository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course with id " + courseId +
                        " not found!"));
        return DozerMapper.parseObject(course, CourseVO.class);
    }

    @Override
    public void deleteCourse(long courseId) {
        Course course = repository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course with id " + courseId +
                        " not found!"));
        repository.deleteCourseById(course.getId());
    }

    @Override
    public CourseVO updateCourse(CourseVO request) {
        Course course = repository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Course with id " + request.getId() +
                        " not found!"));

        course.setTitle(request.getTitle());
        course.setSection(request.getSection());
        course.setYear(request.getYear());
        course.setTeacherId(request.getTeacherId());

        return DozerMapper.parseObject(repository.save(course), CourseVO.class);
    }
}
