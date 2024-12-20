package com.iarasantos.gradeservice.service;

import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface GradeService {
    GradeVO createGrade(GradeVO grade);

    PagedModel<EntityModel<GradeVO>> getGrades(Pageable pageable);

    GradeVO getGradeById(long gradeId);

    void deleteGrade(long gradeId);

    GradeVO updateGrade(GradeVO request);
}
