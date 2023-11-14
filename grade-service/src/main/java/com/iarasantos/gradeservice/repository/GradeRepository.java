package com.iarasantos.gradeservice.repository;

import com.iarasantos.gradeservice.model.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long> {
    Grade findGradeById(Long gradeId);
    void deleteGradeById(Long gradeId);
}
