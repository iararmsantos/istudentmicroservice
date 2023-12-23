package com.iarasantos.courseservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.courseservice.controller.CourseController;
import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.exception.RequiredObjectIsNullException;
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
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }
        Course course = DozerMapper.parseObject(request, Course.class);
        course.setCreationDate(new Date());
        CourseVO vo = DozerMapper.parseObject(repository.save(course), CourseVO.class);

        vo.add(linkTo(methodOn(CourseController.class).getCourse(vo.getKey())).withSelfRel());

        return vo;
    }

    @Override
    public List<CourseVO> getCourses() {
        List<CourseVO> courses = DozerMapper.parseListObjects(repository.findAll(), CourseVO.class);

        courses
                .stream()
                .forEach(course -> course
                        .add(linkTo(methodOn(CourseController.class)
                                .getCourse(course.getKey())).withSelfRel()));

        return courses;
    }

    @Override
    public CourseVO getCourseById(long courseId) {
        Course course = repository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course with id " + courseId +
                        " not found!"));
        CourseVO vo = DozerMapper.parseObject(course, CourseVO.class);

        vo.add(linkTo(methodOn(CourseController.class).getCourse(vo.getKey())).withSelfRel());

        return vo;
    }

    @Override
    public void deleteCourse(long courseId) {
        Course course = repository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course with id " + courseId +
                        " not found!"));
        repository.deleteById(course.getId());
    }

    @Override
    public CourseVO updateCourse(CourseVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }
        Course course = repository.findById(request.getKey()).orElseThrow(
                () -> new ResourceNotFoundException("Course with id " + request.getKey() +
                        " not found!"));

        course.setTitle(request.getTitle());
        course.setSection(request.getSection());
        course.setYear(request.getYear());
        course.setTeacherId(request.getTeacherId());

        CourseVO vo = DozerMapper.parseObject(repository.save(course), CourseVO.class);

        vo.add(linkTo(methodOn(CourseController.class).getCourse(vo.getKey())).withSelfRel());
        return vo;
    }
}
