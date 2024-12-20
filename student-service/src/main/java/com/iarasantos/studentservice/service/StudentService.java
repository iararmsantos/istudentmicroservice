package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface StudentService {
    StudentVO createStudent(StudentVO student);

    PagedModel<EntityModel<StudentVO>> getStudents(Pageable pageable);

    StudentVO getStudentById(Long studentId);

    void deleteStudent(Long studentId);

    StudentVO updateStudent(StudentVO request);
}
