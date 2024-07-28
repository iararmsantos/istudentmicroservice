package com.iarasantos.teacherservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.teacherservice.controller.TeacherController;
import com.iarasantos.teacherservice.data.vo.v1.TeacherVO;
import com.iarasantos.teacherservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.teacherservice.exceptions.ResourceNotFoundException;
import com.iarasantos.teacherservice.model.Role;
import com.iarasantos.teacherservice.model.Teacher;
import com.iarasantos.teacherservice.repository.TeacherRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository repository;

    private ModelMapper modelMapper;

    public TeacherServiceImpl() {
        modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Override
    public TeacherVO create(TeacherVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }

        Teacher teacher = this.modelMapper.map(request, Teacher.class);
        TeacherVO vo = this.modelMapper.map(repository.save(teacher), TeacherVO.class);

        vo.add(linkTo(methodOn(TeacherController.class).getTeacher(vo.getKey())).withSelfRel());
        return vo;
    }

    @Override
    public List<TeacherVO> getTeachers() {
        List<Teacher> teachers = repository.findAll();
        List<TeacherVO> teachersVO = teachers.stream().map(
                (teacher) -> this.modelMapper.map(teacher, TeacherVO.class)
        ).collect(Collectors.toList());
        teachersVO
                .stream()
                .forEach(teacher
                        -> teacher
                        .add(linkTo(methodOn(TeacherController.class)
                                .getTeacher(teacher.getKey())).withSelfRel()));
        return teachersVO;
    }

    @Override
    public TeacherVO getTeacherById(long teacherId) {
        Teacher teacher = repository.findById(teacherId).orElseThrow(
                () -> new ResourceNotFoundException("Teacher with id "
                        + teacherId + " not found."));
        TeacherVO vo = this.modelMapper.map(teacher, TeacherVO.class);

        vo.add(linkTo(methodOn(TeacherController.class).getTeacher(vo.getKey())).withSelfRel());
        return vo;
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        Teacher teacher = repository.findById(teacherId).orElseThrow(
                () -> new ResourceNotFoundException("Teacher with id "
                        + teacherId + " not found."));
        repository.deleteById(teacher.getId());
    }

    @Override
    public TeacherVO updateTeacher(TeacherVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }

        Teacher teacher = repository.findById(request.getKey()).orElseThrow(
                () -> new ResourceNotFoundException("Teacher with id "
                        + request.getKey() + " not found."));
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setPhone(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacher.setRole(Role.TEACHER);

        TeacherVO vo = this.modelMapper.map(repository.save(teacher), TeacherVO.class);

        vo.add(linkTo(methodOn(TeacherController.class).getTeacher(vo.getKey())).withSelfRel());
        return vo;
    }

    private void propertyMapping() {
        TypeMap<Teacher, TeacherVO> propertyMapper = this.modelMapper.createTypeMap(Teacher.class, TeacherVO.class);
        propertyMapper.addMapping(Teacher::getId, TeacherVO::setKey);
        TypeMap<TeacherVO, Teacher> propertyMapper2 = this.modelMapper.createTypeMap(TeacherVO.class, Teacher.class);
        propertyMapper2.addMapping(TeacherVO::getKey, Teacher::setId);
    }
}
