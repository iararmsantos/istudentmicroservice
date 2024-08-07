package com.iarasantos.studentservice.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.unittests.mocks.MockStudent;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelMapperTests {

    MockStudent inputObject;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        inputObject = new MockStudent();
        modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Test
    public void parseEntityToVOTest() {
        StudentVO output = modelMapper.map(inputObject.mockEntity(), StudentVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Email Test0", output.getEmail());
        assertEquals("Phone Test0", output.getPhone());
        assertEquals(Role.STUDENT, output.getRole());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<Student> students = inputObject.mockEntityList();
        List<StudentVO> outputList = students.stream().map((student) ->
                modelMapper.map(student, StudentVO.class))
                .collect(Collectors.toList());
        StudentVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals(Role.STUDENT, outputZero.getRole());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        StudentVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals(Role.STUDENT, outputSeven.getRole());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        StudentVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Phone Test12", outputTwelve.getPhone());
        assertEquals(Role.STUDENT, outputTwelve.getRole());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOToEntityTest() {
        Student output = modelMapper.map(inputObject.mockVO(), Student.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Email Test0", output.getEmail());
        assertEquals("Phone Test0", output.getPhone());
        assertEquals(Role.STUDENT, output.getRole());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOListToEntityListTest() {
        List<StudentVO> students = inputObject.mockVOList();
        List<Student> outputList = students.stream().map((student) ->
                        modelMapper.map(student, Student.class))
                .collect(Collectors.toList());

        Student outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals(Role.STUDENT, outputZero.getRole());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        Student outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals(Role.STUDENT, outputSeven.getRole());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        Student outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Phone Test12", outputTwelve.getPhone());
        assertEquals(Role.STUDENT, outputTwelve.getRole());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }

    private void propertyMapping() {
        TypeMap<Student, StudentVO> propertyMapper = this.modelMapper.createTypeMap(Student.class, StudentVO.class);
        propertyMapper.addMapping(Student::getId, StudentVO::setKey);
        TypeMap<StudentVO, Student> propertyMapper2 = this.modelMapper.createTypeMap(StudentVO.class, Student.class);
        propertyMapper2.addMapping(StudentVO::getKey, Student::setId);
    }
}
