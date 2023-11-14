package com.iarasantos.teacherservice.repository;

import com.iarasantos.teacherservice.model.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    Teacher findTeacherById(Long teacherId);
    void deleteTeacherById(Long teacherId);
}
