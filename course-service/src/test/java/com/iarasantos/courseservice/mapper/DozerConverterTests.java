package com.iarasantos.courseservice.mapper;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.mapper.mocks.MockCourse;
import com.iarasantos.courseservice.model.Course;
import com.iarasantos.courseservice.model.Season;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DozerConverterTests {

    MockCourse inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockCourse();
    }

    @Test
    public void parseEntityToVOTest() {
        CourseVO output = DozerMapper.parseObject(inputObject.mockEntity(), CourseVO.class);
        assertEquals(Long.valueOf(0), output.getKey());
        assertEquals("Title Test0", output.getTitle());
        assertEquals(Season.FALL, output.getSection());
        assertEquals(0, output.getYear());
        assertEquals(Long.valueOf(0), output.getTeacherId());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<CourseVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), CourseVO.class);
        CourseVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("Title Test0", outputZero.getTitle());
        assertEquals(Season.FALL, outputZero.getSection());
        assertEquals(0, outputZero.getYear());
        assertEquals(Long.valueOf(0), outputZero.getTeacherId());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        CourseVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7), outputSeven.getKey());
        assertEquals("Title Test7", outputSeven.getTitle());
        assertEquals(Season.FALL, outputSeven.getSection());
        assertEquals(7, outputSeven.getYear());
        assertEquals(Long.valueOf(7), outputSeven.getTeacherId());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        CourseVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12), outputTwelve.getKey());
        assertEquals("Title Test12", outputTwelve.getTitle());
        assertEquals(Season.FALL, outputTwelve.getSection());
        assertEquals(12, outputTwelve.getYear());
        assertEquals(Long.valueOf(12), outputTwelve.getTeacherId());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOToEntityTest() {
        Course output = DozerMapper.parseObject(inputObject.mockVO(), Course.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Title Test0", output.getTitle());
        assertEquals(Season.FALL, output.getSection());
        assertEquals(0, output.getYear());
        assertEquals(Long.valueOf(0), output.getTeacherId());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOListToEntityListTest() {
        List<Course> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Course.class);
        Course outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0), outputZero.getId());
        assertEquals("Title Test0", outputZero.getTitle());
        assertEquals(Season.FALL, outputZero.getSection());
        assertEquals(0, outputZero.getYear());
        assertEquals(Long.valueOf(0), outputZero.getTeacherId());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        Course outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7), outputSeven.getId());
        assertEquals("Title Test7", outputSeven.getTitle());
        assertEquals(Season.FALL, outputSeven.getSection());
        assertEquals(7, outputSeven.getYear());
        assertEquals(Long.valueOf(7), outputSeven.getTeacherId());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        Course outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12), outputTwelve.getId());
        assertEquals("Title Test12", outputTwelve.getTitle());
        assertEquals(Season.FALL, outputTwelve.getSection());
        assertEquals(12, outputTwelve.getYear());
        assertEquals(Long.valueOf(12), outputTwelve.getTeacherId());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }
}
