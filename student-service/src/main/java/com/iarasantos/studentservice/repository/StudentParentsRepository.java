package com.iarasantos.studentservice.repository;

import com.iarasantos.studentservice.model.StudentParent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentParentsRepository extends JpaRepository<StudentParent, Long> {
List<StudentParent> findByStudentId(long studentId);
}
