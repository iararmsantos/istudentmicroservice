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

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    private PagedResourcesAssembler<TeacherVO> assembler;

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
    public PagedModel<EntityModel<TeacherVO>> getTeachers(Pageable pageable) {
        // Fetch paginated Student entities from the repository
        Page<Teacher> studentPage = repository.findAll(pageable);

        // Map each Student to a StudentVO
        Page<TeacherVO> studentVoPage = studentPage.map(s -> this.modelMapper.map(s, TeacherVO.class));

        // Add self-links to each StudentVO
        studentVoPage.forEach(p -> p.add(
                linkTo(methodOn(TeacherController.class)
                        .getTeacher(p.getKey())
                ).withSelfRel()
        ));

        // add pagination hateoas link
        Link link = linkTo(methodOn(TeacherController.class).getTeachers(pageable.getPageNumber(),
                pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(studentVoPage, link);
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
