package com.iarasantos.gradeservice.service;

import com.iarasantos.gradeservice.dto.GradeResponse;
import com.iarasantos.gradeservice.model.Grade;
import com.iarasantos.gradeservice.dto.GradeRequest;
import java.util.List;

public interface GradeService {
    public Grade createGrade(GradeRequest grade);
    public List<GradeResponse> getGrades();
    public Grade getGradeById(long gradeId);

    public void deleteGrade(long gradeId);
    public void updateGrade(Grade grade, GradeRequest request);
}
