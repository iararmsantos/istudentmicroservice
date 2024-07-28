package com.iarasantos.gradeservice.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.unittests.mocks.MockGrade;
import com.iarasantos.gradeservice.model.Grade;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelMapperTests {

    MockGrade inputObject;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        inputObject = new MockGrade();
        modelMapper = new ModelMapper();

    }

    @Test
    public void parseEntityToVOTest() {
    Grade grade = inputObject.mockEntity();
        propertyMapping();
        GradeVO output = this.modelMapper.map(grade, GradeVO.class);
        assertEquals(Long.valueOf(0), output.getKey());
        assertEquals("A", output.getLetterGrade());
        assertEquals(Double.valueOf(0), output.getNumberGrade());
        assertEquals(Long.valueOf(0), output.getStudentId());
        assertEquals(Long.valueOf(0), output.getCourseId());

        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<Grade> grades = inputObject.mockEntityList();
        propertyMapping();
        List<GradeVO> outputList = grades.stream().map((grade) ->
                this.modelMapper.map(grade, GradeVO.class))
                .collect(Collectors.toList());
        GradeVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("A", outputZero.getLetterGrade());
        assertEquals(Double.valueOf(0), outputZero.getNumberGrade());
        assertEquals(Long.valueOf(0), outputZero.getStudentId());
        assertEquals(Long.valueOf(0), outputZero.getCourseId());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        GradeVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("A", outputSeven.getLetterGrade());
        assertEquals(Double.valueOf(7), outputSeven.getNumberGrade());
        assertEquals(Long.valueOf(7), outputSeven.getStudentId());
        assertEquals(Long.valueOf(7), outputSeven.getCourseId());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        GradeVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12), outputTwelve.getKey());
        assertEquals("A", outputTwelve.getLetterGrade());
        assertEquals(Double.valueOf(12), outputTwelve.getNumberGrade());
        assertEquals(Long.valueOf(12), outputTwelve.getStudentId());
        assertEquals(Long.valueOf(12), outputTwelve.getCourseId());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOToEntityTest() {
        GradeVO grade = inputObject.mockVO();
        propertyMapping();
        Grade output = this.modelMapper.map(grade, Grade.class);
        assertEquals(Long.valueOf(0), output.getId());
        assertEquals("A", output.getLetterGrade());
        assertEquals(Double.valueOf(0), output.getNumberGrade());
        assertEquals(Long.valueOf(0), output.getStudentId());
        assertEquals(Long.valueOf(0), output.getCourseId());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOListToEntityListTest() {
        List<GradeVO> grades = inputObject.mockVOList();
        propertyMapping();
        List<Grade> outputList = grades.stream().map((grade) ->
                this.modelMapper.map(grade, Grade.class))
                .collect(Collectors.toList());
        Grade outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0), outputZero.getId());
        assertEquals("A", outputZero.getLetterGrade());
        assertEquals(Double.valueOf(0), outputZero.getNumberGrade());
        assertEquals(Long.valueOf(0), outputZero.getStudentId());
        assertEquals(Long.valueOf(0), outputZero.getCourseId());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        Grade outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("A", outputSeven.getLetterGrade());
        assertEquals(Double.valueOf(7), outputSeven.getNumberGrade());
        assertEquals(Long.valueOf(7), outputSeven.getStudentId());
        assertEquals(Long.valueOf(7), outputSeven.getCourseId());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        Grade outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12), outputTwelve.getId());
        assertEquals("A", outputTwelve.getLetterGrade());
        assertEquals(Double.valueOf(12), outputTwelve.getNumberGrade());
        assertEquals(Long.valueOf(12), outputTwelve.getStudentId());
        assertEquals(Long.valueOf(12), outputTwelve.getCourseId());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }

    private void propertyMapping() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TypeMap<Grade, GradeVO> propertyMapper = this.modelMapper.createTypeMap(Grade.class, GradeVO.class);
        propertyMapper.addMapping(Grade::getId, GradeVO::setKey);
        TypeMap<GradeVO, Grade> propertyMapperVO = this.modelMapper.createTypeMap(GradeVO.class, Grade.class);
        propertyMapperVO.addMapping(GradeVO::getKey, Grade::setId);

    }
}
