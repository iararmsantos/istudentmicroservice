package com.iarasantos.teacherservice.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.teacherservice.data.vo.v1.TeacherVO;
import com.iarasantos.teacherservice.mapper.mocks.MockTeacher;
import com.iarasantos.teacherservice.model.Role;
import com.iarasantos.teacherservice.model.Teacher;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DozerConverterTests {

    MockTeacher inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockTeacher();
    }

    @Test
    public void parseEntityToVOTest() {
        TeacherVO output = DozerMapper.parseObject(inputObject.mockEntity(), TeacherVO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Email Test0", output.getEmail());
        assertEquals("Phone Test0", output.getPhone());
        assertEquals(Role.TEACHER, output.getRole());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<TeacherVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), TeacherVO.class);
        TeacherVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals(Role.TEACHER, outputZero.getRole());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        TeacherVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals(Role.TEACHER, outputSeven.getRole());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        TeacherVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Phone Test12", outputTwelve.getPhone());
        assertEquals(Role.TEACHER, outputTwelve.getRole());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOToEntityTest() {
        Teacher output = DozerMapper.parseObject(inputObject.mockVO(), Teacher.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Email Test0", output.getEmail());
        assertEquals("Phone Test0", output.getPhone());
        assertEquals(Role.TEACHER, output.getRole());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOListToEntityListTest() {
        List<Teacher> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Teacher.class);
        Teacher outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals(Role.TEACHER, outputZero.getRole());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        Teacher outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals(Role.TEACHER, outputSeven.getRole());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        Teacher outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Phone Test12", outputTwelve.getPhone());
        assertEquals(Role.TEACHER, outputTwelve.getRole());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }
}
