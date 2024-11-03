package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import java.util.List;

public interface StudentService {
    StudentVO createStudent(StudentVO student);

    List<StudentVO> getStudents();

    StudentVO getStudentById(Long studentId);

    void deleteStudent(Long studentId);

    StudentVO updateStudent(StudentVO request);
}
