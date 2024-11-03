package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.data.vo.v1.StudentParentRequest;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.model.Student;
import java.util.List;

public interface StudentService {
    public StudentVO createStudent(StudentVO student);

    public List<StudentVO> getStudents();

    public StudentVO getStudentById(Long studentId);

    public void deleteStudent(Long studentId);

    public StudentVO updateStudent(StudentVO request);
}
