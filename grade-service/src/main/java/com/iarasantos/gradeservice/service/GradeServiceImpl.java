package com.iarasantos.gradeservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.gradeservice.controller.GradeController;
import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.exception.RequiredObjectIsNullException;
import com.iarasantos.gradeservice.exception.ResourceNotFoundException;
import com.iarasantos.gradeservice.model.Grade;
import com.iarasantos.gradeservice.repository.GradeRepository;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    private PagedResourcesAssembler<GradeVO> assembler;

    public GradeServiceImpl() {
        this.modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Override
    public GradeVO createGrade(GradeVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }

        Grade grade = this.modelMapper.map(request, Grade.class);
        grade.setCreationDate(new Date());
        GradeVO vo = this.modelMapper.map(repository.save(grade), GradeVO.class);

        vo.add(linkTo(methodOn(GradeController.class).getGrade(vo.getKey())).withSelfRel());
        return vo;
    }

    @Override
    public PagedModel<EntityModel<GradeVO>> getGrades(Pageable pageable) {
        Page<Grade> grades = repository.findAll(pageable);
        Page<GradeVO> gradesVO = grades.map(
                (grade) -> this.modelMapper.map(grade, GradeVO.class));

        gradesVO
                .forEach(grade -> grade
                        .add(linkTo(methodOn(GradeController.class)
                                .getGrade(grade.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(GradeController.class)
                .getGrades(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
                .withSelfRel();

        return assembler.toModel(gradesVO, link);
    }

    @Override
    public GradeVO getGradeById(long gradeId) {
        Grade grade = repository.findById(gradeId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + gradeId + " not found!"));

        GradeVO vo = this.modelMapper.map(grade, GradeVO.class);

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
        GradeVO vo = this.modelMapper.map(repository.save(grade), GradeVO.class);

        vo.add(linkTo(methodOn(GradeController.class).getGrade(vo.getKey())).withSelfRel());

        return vo;
    }

    private void propertyMapping() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TypeMap<Grade, GradeVO> propertyMapper = this.modelMapper.createTypeMap(Grade.class, GradeVO.class);
        propertyMapper.addMapping(Grade::getId, GradeVO::setKey);
        TypeMap<GradeVO, Grade> propertyMapperVO = this.modelMapper.createTypeMap(GradeVO.class, Grade.class);
        propertyMapperVO.addMapping(GradeVO::getKey, Grade::setId);
    }
}
