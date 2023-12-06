package com.iarasantos.gradeservice.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.mapper.mocks.MockGrade;
import com.iarasantos.gradeservice.model.Grade;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DozerConverterTests {

    MockGrade inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockGrade();
    }

    @Test
    public void parseEntityToVOTest() {
        GradeVO output = DozerMapper.parseObject(inputObject.mockEntity(), GradeVO.class);
        assertEquals(Long.valueOf(0), output.getId());
        assertEquals("A", output.getLetterGrade());
        assertEquals(Double.valueOf(0), output.getNumberGrade());
        assertEquals(Long.valueOf(0), output.getStudentId());
        assertEquals(Long.valueOf(0), output.getCourseId());

        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<GradeVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), GradeVO.class);
        GradeVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("A", outputZero.getLetterGrade());
        assertEquals(Double.valueOf(0), outputZero.getNumberGrade());
        assertEquals(Long.valueOf(0), outputZero.getStudentId());
        assertEquals(Long.valueOf(0), outputZero.getCourseId());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        GradeVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("A", outputSeven.getLetterGrade());
        assertEquals(Double.valueOf(7), outputSeven.getNumberGrade());
        assertEquals(Long.valueOf(7), outputSeven.getStudentId());
        assertEquals(Long.valueOf(7), outputSeven.getCourseId());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        GradeVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12), outputTwelve.getId());
        assertEquals("A", outputTwelve.getLetterGrade());
        assertEquals(Double.valueOf(12), outputTwelve.getNumberGrade());
        assertEquals(Long.valueOf(12), outputTwelve.getStudentId());
        assertEquals(Long.valueOf(12), outputTwelve.getCourseId());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOToEntityTest() {
        Grade output = DozerMapper.parseObject(inputObject.mockVO(), Grade.class);
        assertEquals(Long.valueOf(0), output.getId());
        assertEquals("A", output.getLetterGrade());
        assertEquals(Double.valueOf(0), output.getNumberGrade());
        assertEquals(Long.valueOf(0), output.getStudentId());
        assertEquals(Long.valueOf(0), output.getCourseId());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOListToEntityListTest() {
        List<Grade> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Grade.class);
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
}
