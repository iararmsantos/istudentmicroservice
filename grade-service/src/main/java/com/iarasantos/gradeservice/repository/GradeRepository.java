package com.iarasantos.gradeservice.repository;

import com.iarasantos.gradeservice.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Grade findGradeById(Long gradeId);

    void deleteGradeById(Long gradeId);
}
