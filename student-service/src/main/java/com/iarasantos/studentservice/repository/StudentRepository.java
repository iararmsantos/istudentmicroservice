package com.iarasantos.studentservice.repository;

import com.iarasantos.studentservice.model.ParentList;
import com.iarasantos.studentservice.model.Student;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findStudentById(Long studentId);

    void deleteById(Long studentId);

    

}
