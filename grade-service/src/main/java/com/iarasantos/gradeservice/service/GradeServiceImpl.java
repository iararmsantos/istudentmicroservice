package com.iarasantos.gradeservice.service;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.exception.ResourceNotFoundException;
import com.iarasantos.gradeservice.model.Grade;
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
    public GradeVO createGrade(GradeVO request) {
        Grade grade = DozerMapper.parseObject(request, Grade.class);
        grade.setCreationDate(new Date());
        return DozerMapper.parseObject(repository.save(grade), GradeVO.class);
    }

    @Override
    public List<GradeVO> getGrades() {
        return DozerMapper.parseListObjects(repository.findAll(), GradeVO.class);
    }

    @Override
    public GradeVO getGradeById(long gradeId) {
        Grade grade = repository.findById(gradeId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + gradeId + " not found!"));

        return DozerMapper.parseObject(grade, GradeVO.class);
    }

    @Override
    public void deleteGrade(long gradeId) {
        repository.deleteGradeById(gradeId);
    }

    @Override
    public GradeVO updateGrade(GradeVO request) {
        Grade grade = repository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + request.getId() + " not found!"));
        grade.setLetterGrade(request.getLetterGrade());
        grade.setNumberGrade(request.getNumberGrade());
        grade.setStudentId(request.getStudentId());
        grade.setCourseId(request.getCourseId());
        return DozerMapper.parseObject(repository.save(grade), GradeVO.class);
    }
}
