package com.iarasantos.courseservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.courseservice.controller.CourseController;
import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.exception.RequiredObjectIsNullException;
import com.iarasantos.courseservice.exception.ResourceNotFoundException;
import com.iarasantos.courseservice.repository.CourseRepository;
import com.iarasantos.courseservice.model.Course;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    private ModelMapper modelMapper;

    public CourseServiceImpl() {
        this.modelMapper = new ModelMapper();
        propertyMapping();
    }



    @Override
    public CourseVO createCourse(CourseVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }

        Course course = this.modelMapper.map(request, Course.class);
        course.setCreationDate(new Date());
        CourseVO vo = this.modelMapper.map(repository.save(course), CourseVO.class);

        vo.add(linkTo(methodOn(CourseController.class).getCourse(vo.getKey())).withSelfRel());

        return vo;
    }

    @Override
    public List<CourseVO> getCourses() {

        List<Course> courses = repository.findAll();
        List<CourseVO> coursesVO = courses.stream()
                .map((course) -> this.modelMapper.map(course, CourseVO.class))
                .collect(Collectors.toList());

        coursesVO
                .stream()
                .forEach(course -> course
                        .add(linkTo(methodOn(CourseController.class)
                                .getCourse(course.getKey())).withSelfRel()));

        return coursesVO;
    }

    @Override
    public CourseVO getCourseById(long courseId) {


        Course course = repository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course with id " + courseId +
                        " not found!"));
        CourseVO vo = this.modelMapper.map(course, CourseVO.class);

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

        CourseVO vo = this.modelMapper.map(repository.save(course), CourseVO.class);

        vo.add(linkTo(methodOn(CourseController.class).getCourse(vo.getKey())).withSelfRel());
        return vo;
    }

    private void propertyMapping() {
        TypeMap<Course, CourseVO> propertyMapper = this.modelMapper.createTypeMap(Course.class, CourseVO.class);
        propertyMapper.addMapping(Course::getId, CourseVO::setKey);
    }
}
