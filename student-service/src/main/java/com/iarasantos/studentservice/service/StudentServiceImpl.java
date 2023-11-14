package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.dto.ParentListRequest;
import com.iarasantos.studentservice.dto.StudentParentRequest;
import com.iarasantos.studentservice.dto.StudentRequest;
import com.iarasantos.studentservice.model.ParentList;
import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.repository.ParentListRepository;
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
    private ParentListRepository parentRepository;

    @Override
    public Student createStudent(Student student) {
        student.setRole(Role.STUDENT);
        student.setCreationDate(new Date());

        return repository.save(student);
    }

    @Override
    public List<Student> getStudents() {
        return (List<Student>) repository.findAll();
    }

    @Override
    public Student getStudentById(Long studentId) {
        return repository.findStudentById(studentId);
    }

    @Override
    public void deleteStudent(Long studentId) {
        repository.deleteById(studentId);

    }

    @Override
    public void updateStudent(Student student, StudentRequest request) {
        setStudentUpdate(student, request);
        repository.save(student);
    }

    @Override
    public void validateStudent(String studentId) {
        repository.findById(Long.valueOf(studentId)).orElseThrow();
    }

    @Override
    public List<ParentList> createStudentWithParents(StudentParentRequest request) {
        Student student = request.getStudent();
        student.setRole(Role.STUDENT);
        student.setCreationDate(new Date());

        Student saved = repository.save(student);
        List<ParentList> parents = request.getParents().stream()
                .map(parent -> mapToParent(parent, saved)).toList();

        List<ParentList> newParents =  parents.stream().map(p -> parentRepository.save(p)).toList();
        return  newParents;

    }

    private ParentList mapToParent(ParentList parent, Student student) {
        return ParentList.builder()
                .parentId(parent.getParentId())
                .student(student)
                .build();
    }


    private void setStudentUpdate(Student student, StudentRequest request) {
        if (request.getFirstName() != null) {
            student.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            student.setLastName(request.getLastName());
        }

        if (request.getPhone() != null) {
            student.setPhone(request.getPhone());
        }

        if (request.getEmail() != null) {
            student.setEmail(request.getEmail());
        }

        if (request.getRole() != null) {
            student.setRole(Role.STUDENT);
        }
    }
}
