package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.model.StudentParent;

import java.util.List;

public interface StudentParentsService {

    void deleteStudentParents(Long studentId);

    List<StudentParent> createParents(StudentVO studentVO, long studentId);

    List<StudentParent> updateParents(List<StudentParent> studentParents, long studentId);
}
