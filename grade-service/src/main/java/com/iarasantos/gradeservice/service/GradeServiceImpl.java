package com.iarasantos.gradeservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.gradeservice.controller.GradeController;
import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.exception.RequiredObjectIsNullException;
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
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }
        Grade grade = DozerMapper.parseObject(request, Grade.class);
        grade.setCreationDate(new Date());
        GradeVO vo = DozerMapper.parseObject(repository.save(grade), GradeVO.class);

        vo.add(linkTo(methodOn(GradeController.class).getGrade(vo.getKey())).withSelfRel());
        return vo;
    }

    @Override
    public List<GradeVO> getGrades() {
        List<GradeVO> grades = DozerMapper.parseListObjects(repository.findAll(), GradeVO.class);

        grades
                .stream()
                .forEach(grade -> grade
                        .add(linkTo(methodOn(GradeController.class)
                                .getGrade(grade.getKey())).withSelfRel()));

        return grades;
    }

    @Override
    public GradeVO getGradeById(long gradeId) {
        Grade grade = repository.findById(gradeId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + gradeId + " not found!"));

        GradeVO vo = DozerMapper.parseObject(grade, GradeVO.class);

        vo.add(linkTo(methodOn(GradeController.class).getGrade(vo.getKey())).withSelfRel());
        return vo;
    }

    @Override
    public void deleteGrade(long gradeId) {
        Grade grade = repository.findById(gradeId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + gradeId + " not found!"));
        repository.deleteById(grade.getId());
    }

    @Override
    public GradeVO updateGrade(GradeVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }
        Grade grade = repository.findById(request.getKey()).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + request.getKey() + " not found!"));
        grade.setLetterGrade(request.getLetterGrade());
        grade.setNumberGrade(request.getNumberGrade());
        grade.setStudentId(request.getStudentId());
        grade.setCourseId(request.getCourseId());
        GradeVO vo = DozerMapper.parseObject(repository.save(grade), GradeVO.class);

        vo.add(linkTo(methodOn(GradeController.class).getGrade(vo.getKey())).withSelfRel());

        return vo;
    }
}
