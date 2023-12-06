package com.iarasantos.gradeservice.mapper.mocks;

import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.model.Grade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockGrade {

    public Grade mockEntity() {
        return mockEntity(0);
    }

    public GradeVO mockVO() {
        return mockVO(0);
    }

    public List<GradeVO> mockVOList() {
        List<GradeVO> grades = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            grades.add(mockVO(i));
        }
        return grades;
    }

    public List<Grade> mockEntityList() {
        List<Grade> grades = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            grades.add(mockEntity(i));
        }
        return grades;
    }

    public Grade mockEntity(Integer number) {
        Grade grade = new Grade();
        grade.setId(Long.valueOf(number));
        grade.setLetterGrade("A");
        grade.setNumberGrade(Double.valueOf(number));
        grade.setStudentId(Long.valueOf(number));
        grade.setCourseId(Long.valueOf(number));

        grade.setCreationDate(new Date());

        return grade;
    }

    public GradeVO mockVO(Integer number) {
        GradeVO grade = new GradeVO();
        grade.setId(Long.valueOf(number));
        grade.setLetterGrade("A");
        grade.setNumberGrade(Double.valueOf(number));
        grade.setStudentId(Long.valueOf(number));
        grade.setCourseId(Long.valueOf(number));

        grade.setCreationDate(new Date());

        return grade;
    }
}
