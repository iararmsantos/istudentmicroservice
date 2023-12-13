package com.iarasantos.studentservice.service;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.studentservice.data.vo.v1.StudentParentRequest;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.exceptions.ResourceNotFoundException;
import com.iarasantos.studentservice.model.StudentParent;
import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.repository.StudentParentsRepository;
import com.iarasantos.studentservice.repository.StudentRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentParentsRepository parentRepository;

    @Override
    public StudentVO createStudent(StudentVO student) {
        Student std = DozerMapper.parseObject(student, Student.class);

        std.setCreationDate(new Date());
        return DozerMapper.parseObject(repository.save(std), StudentVO.class);
    }

    @Override
    public List<StudentVO> getStudents() {
        return DozerMapper.parseListObjects(repository.findAll(), StudentVO.class);
    }

    @Override
    public StudentVO getStudentById(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + studentId + " not found!"));
        return DozerMapper.parseObject(student, StudentVO.class);
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + studentId + " not found!"));
        repository.deleteById(studentId);
        parentRepository.deleteByStudentId(student.getId());
    }

    @Override
    public StudentVO updateStudent(StudentVO request) {
        Student student = repository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + request.getId() + " not found!"));

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        student.setRole(request.getRole());

        return DozerMapper.parseObject(repository.save(student), StudentVO.class);
    }

    @Override
    public StudentParentRequest createStudentWithParents(StudentParentRequest request) {
        Student student = request.getStudent();
        student.setRole(Role.STUDENT);
        student.setCreationDate(new Date());

        Student saved = repository.save(student);
        List<StudentParent> parents = request.getParents().stream()
                .map(parent -> mapToParent(parent, saved)).toList();

        List<StudentParent> newParents =  parents.stream().map(p -> parentRepository.save(p)).toList();
        StudentParentRequest str = new StudentParentRequest();
        str.setStudent(saved);
        str.setParents(newParents);
        return  str;
    }

    private StudentParent mapToParent(StudentParent parent, Student student) {
        return StudentParent.builder()
                .parentId(parent.getParentId())
                .studentId(student.getId())
                .build();
    }
}
