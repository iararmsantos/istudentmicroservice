package com.iarasantos.courseservice.unittests.mapper;

import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.model.Course;
import com.iarasantos.courseservice.model.Season;
import com.iarasantos.courseservice.unittests.mocks.MockCourse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelMapperTests {

    MockCourse inputObject;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        inputObject = new MockCourse();
        modelMapper = new ModelMapper();
    }

    @Test
    public void parseEntityToVOTest() {
        Course course = inputObject.mockEntity();
        propertyMapping();
        CourseVO output = this.modelMapper.map(course, CourseVO.class);
        assertEquals(Long.valueOf(0), output.getKey());
        assertEquals("Title Test0", output.getTitle());
        assertEquals(Season.FALL, output.getSection());
        assertEquals(0, output.getYear());
        assertEquals(Long.valueOf(0), output.getTeacherId());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<Course> courses = inputObject.mockEntityList();
        propertyMapping();
        List<CourseVO> outputList = courses.stream()
                .map((course) -> this.modelMapper.map(course, CourseVO.class))
                .collect(Collectors.toList());

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
        CourseVO courseVO = inputObject.mockVO();
        Course output = this.modelMapper.map(courseVO, Course.class);

        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Title Test0", output.getTitle());
        assertEquals(Season.FALL, output.getSection());
        assertEquals(0, output.getYear());
        assertEquals(Long.valueOf(0), output.getTeacherId());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOListToEntityListTest() {
        List<CourseVO> courses = inputObject.mockVOList();
        List<Course> outputList = courses.stream()
                .map((course) -> this.modelMapper.map(course, Course.class))
                .collect(Collectors.toList());
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

    private void propertyMapping() {
        TypeMap<Course, CourseVO> propertyMapper = this.modelMapper.createTypeMap(Course.class, CourseVO.class);
        propertyMapper.addMapping(Course::getId, CourseVO::setKey);
    }
}
