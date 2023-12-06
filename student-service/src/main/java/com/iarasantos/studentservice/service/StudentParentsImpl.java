package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.data.vo.v1.StudentParentRequest;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.exceptions.ResourceNotFoundException;
import com.iarasantos.studentservice.mapper.DozerMapper;
import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.model.StudentParent;
import com.iarasantos.studentservice.repository.StudentParentsRepository;
import com.iarasantos.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StudentParentsImpl implements StudentParentsService {
    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentParentsRepository parentRepository;

    @Override
    public void deleteStudentParents(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + studentId + " not found!"));

        parentRepository.deleteByStudentId(student.getId());
    }

    @Override
    public StudentParentRequest createStudentWithParents(StudentParentRequest request) {
        Student student = request.getStudent();
        student.setRole(Role.STUDENT);
        student.setCreationDate(new Date());

        Student saved = repository.save(student);
        List<StudentParent> parents = request.getParents().stream()
                .map(parent -> mapToParent(parent, saved)).toList();

        List<StudentParent> newParents = parents.stream().map(p -> parentRepository.save(p)).toList();
        StudentParentRequest str = new StudentParentRequest();
        str.setStudent(saved);
        str.setParents(newParents);
        return str;
    }

    private StudentParent mapToParent(StudentParent parent, Student student) {
        return StudentParent.builder()
                .parentId(parent.getParentId())
                .studentId(student.getId())
                .build();
    }
}
