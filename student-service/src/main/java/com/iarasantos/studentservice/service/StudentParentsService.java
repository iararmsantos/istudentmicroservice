package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.data.vo.v1.StudentParentRequest;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;

import java.util.List;

public interface StudentParentsService {

    public void deleteStudentParents(Long studentId);


    public StudentParentRequest createStudentWithParents(StudentParentRequest studentParentRequest);
}
