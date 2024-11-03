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
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    private StudentParentsRepository parentRepository;

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
    public List<StudentVO> getStudents() {
        List<Student> students = repository.findAll();
        List<StudentVO> studentsVO = students.stream().map(
                (student) -> this.modelMapper.map(student, StudentVO.class)
        ).collect(Collectors.toList());

        //hateoas
        studentsVO
                .stream()
                .forEach(s -> s
                        .add(linkTo(methodOn(StudentController.class)
                                .getStudent(s.getKey())).withSelfRel()));
        return studentsVO;
    }

    @Override
    public StudentVO getStudentById(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + studentId + " not found!"));
        StudentVO vo = this.modelMapper.map(student, StudentVO.class);
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
//        parentRepository.deleteById(student.getId());

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

        //hateoas
        vo.add(linkTo(methodOn(StudentController.class).getStudent(vo.getKey())).withSelfRel());

        return vo;
    }

    private StudentParent mapToParent(StudentParent parent, Student student) {
        return StudentParent.builder()
                .parentId(parent.getParentId())
                .studentId(student.getId())
                .build();
    }

    private void propertyMapping() {
        TypeMap<Student, StudentVO> propertyMapper = this.modelMapper.createTypeMap(Student.class, StudentVO.class);
        propertyMapper.addMapping(Student::getId, StudentVO::setKey);
        TypeMap<StudentVO, Student> propertyMapper2 = this.modelMapper.createTypeMap(StudentVO.class, Student.class);
        propertyMapper2.addMapping(StudentVO::getKey, Student::setId);
    }
}
