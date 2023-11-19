package com.iarasantos.gradeservice.service;

import com.iarasantos.gradeservice.dto.GradeResponse;
import com.iarasantos.gradeservice.model.Grade;
import com.iarasantos.gradeservice.dto.GradeRequest;
import com.iarasantos.gradeservice.repository.GradeRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository repository;

    @Override
    public Grade createGrade(GradeRequest request) {
        Grade grade = Grade.builder()
                .letterGrade(request.getLetterGrade())
                .numberGrade(request.getNumberGrade())
                .courseId(request.getCourseId())
                .studentId(request.getStudentId())
                .build();
        grade.setCreationDate(new Date());
        return repository.save(grade);
    }

    @Override
    public List<GradeResponse> getGrades() {
        List<Grade> grades = (List<Grade>) repository.findAll();
        return grades.stream().map(grade -> mapToGradeResponse(grade)).toList();
    }

    private GradeResponse mapToGradeResponse(Grade grade) {
        return GradeResponse.builder()
                .id(grade.getId())
                .letterGrade(grade.getLetterGrade())
                .numberGrade(grade.getNumberGrade())
                .creationDate(grade.getCreationDate())
                .courseId(grade.getCourseId())
                .studentId(grade.getStudentId())
                .build();
    }

    @Override
    public Grade getGradeById(long gradeId) {
        return repository.findGradeById(gradeId);
    }

    @Override
    public void deleteGrade(long gradeId) {
        repository.deleteGradeById(gradeId);
    }

    @Override
    public void updateGrade(Grade grade, GradeRequest request) {
        setGradeUpdate(grade, request);
        repository.save(grade);
    }

    private void setGradeUpdate(Grade grade, GradeRequest request) {
        if (request.getLetterGrade() != null) {
            grade.setLetterGrade(request.getLetterGrade());
        }

        if(request.getNumberGrade() != null) {
            grade.setNumberGrade(request.getNumberGrade());
        }
    }
}
