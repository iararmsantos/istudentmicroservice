package com.iarasantos.gradeservice.service;

import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import java.util.List;

public interface GradeService {
    public GradeVO createGrade(GradeVO grade);
    public List<GradeVO> getGrades();
    public GradeVO getGradeById(long gradeId);

    public void deleteGrade(long gradeId);
    public GradeVO updateGrade(GradeVO request);
}
