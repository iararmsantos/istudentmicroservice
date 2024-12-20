package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.controller.StudentController;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.studentservice.exceptions.ResourceNotFoundException;
import com.iarasantos.studentservice.model.StudentParent;
import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.repository.StudentParentsRepository;
import com.iarasantos.studentservice.repository.StudentRepository;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    private StudentParentsRepository parentRepository;

    @Autowired
    private PagedResourcesAssembler<StudentVO> assembler;

    @Autowired
    private StudentParentsService studentParentsService;

    public StudentServiceImpl() {
        modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Override
    public StudentVO createStudent(StudentVO student) {
        if(student == null) {
            throw new RequiredObjectIsNullException();
        }
        Student std = this.modelMapper.map(student, Student.class);
        std.setRole(Role.STUDENT);
        std.setCreationDate(new Date());
        StudentVO vo = this.modelMapper.map(repository.save(std), StudentVO.class);

        List<StudentParent> studentParents = studentParentsService.createParents(student, vo.getKey());
        vo.setStudentParents(studentParents);


        //hateoas
        vo.add(linkTo(methodOn(StudentController.class).getStudent(vo.getKey())).withSelfRel());

        return vo;
    }

    @Override
    public PagedModel<EntityModel<StudentVO>> getStudents(Pageable pageable) {
        // Fetch paginated Student entities from the repository
        Page<Student> studentPage = repository.findAll(pageable);

        // Map each Student to a StudentVO
        Page<StudentVO> studentVoPage = studentPage.map(s -> this.modelMapper.map(s, StudentVO.class));

        // Add self-links to each StudentVO
        studentVoPage.forEach(p -> p.add(
                linkTo(methodOn(StudentController.class)
                        .getStudent(p.getKey())
                ).withSelfRel()
        ));

        // add pagination hateoas link
        Link link = linkTo(methodOn(StudentController.class).getStudents(pageable.getPageNumber(),
                pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(studentVoPage, link);

    }

    @Override
    public StudentVO getStudentById(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + studentId + " not found!"));
        StudentVO vo = this.modelMapper.map(student, StudentVO.class);

        List<StudentParent> parents = parentRepository.findByStudentId(vo.getKey());
        vo.setStudentParents(parents);

        //hateoas
        vo.add(linkTo(methodOn(StudentController.class).getStudent(studentId)).withSelfRel());
        return vo;
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + studentId + " not found!"));
        repository.deleteById(studentId);

        studentParentsService.deleteStudentParents(studentId);
    }

    @Override
    public StudentVO updateStudent(StudentVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }
        Student student = repository.findById(request.getKey()).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + request.getKey() + " not found!"));

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        student.setRole(Role.STUDENT);

        StudentVO vo = this.modelMapper.map(repository.save(student), StudentVO.class);

        List<StudentParent> parents = studentParentsService.updateParents(request.getStudentParents(), vo.getKey());
        vo.setStudentParents(parents);

        //hateoas
        vo.add(linkTo(methodOn(StudentController.class).getStudent(vo.getKey())).withSelfRel());

        return vo;
    }

    private void propertyMapping() {
        TypeMap<Student, StudentVO> propertyMapper = this.modelMapper.createTypeMap(Student.class, StudentVO.class);
        propertyMapper.addMapping(Student::getId, StudentVO::setKey);
        TypeMap<StudentVO, Student> propertyMapper2 = this.modelMapper.createTypeMap(StudentVO.class, Student.class);
        propertyMapper2.addMapping(StudentVO::getKey, Student::setId);
    }
}
