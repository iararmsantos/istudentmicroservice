package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.dto.StudentParentRequest;
import com.iarasantos.studentservice.dto.StudentRequest;
import com.iarasantos.studentservice.model.ParentList;
import com.iarasantos.studentservice.model.Student;
import java.util.List;

public interface StudentService {
    public Student createStudent(Student student);

    public List<Student> getStudents();

    public Student getStudentById(Long studentId);

    public void deleteStudent(Long studentId);

    public void updateStudent(Student student, StudentRequest request);

    public void validateStudent(String studentId);

    public List<ParentList> createStudentWithParents(StudentParentRequest studentParentRequest);
}
