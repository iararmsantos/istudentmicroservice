package com.iarasantos.studentservice.repository;

import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.model.StudentParent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentParentsRepository extends JpaRepository<StudentParent, Long> {

}
