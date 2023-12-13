package com.iarasantos.teacherservice.repository;

import com.iarasantos.teacherservice.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findTeacherById(Long teacherId);

    void deleteTeacherById(Long teacherId);
}
