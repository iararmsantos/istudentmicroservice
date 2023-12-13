package com.iarasantos.loginservice.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.loginservice.data.vo.v1.UserVO;
import com.iarasantos.loginservice.mapper.mocks.MockUser;
import com.iarasantos.loginservice.model.Role;
import com.iarasantos.loginservice.model.User;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DozerConverterTests {

    MockUser inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockUser();
    }

    @Test
    public void parseEntityToVOTest() {
        UserVO output = DozerMapper.parseObject(inputObject.mockEntity(), UserVO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Email Test0", output.getEmail());
        assertEquals("Phone Test0", output.getPhone());
        assertEquals(Role.STUDENT, output.getRole());
        assertTrue((new Date().getTime() - output.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<UserVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), UserVO.class);
        UserVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals(Role.STUDENT, outputZero.getRole());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        UserVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals(Role.STUDENT, outputSeven.getRole());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        UserVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Phone Test12", outputTwelve.getPhone());
        assertEquals(Role.STUDENT, outputTwelve.getRole());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }

    @Test
    public void parseVOToEntityTest() {
        User output = DozerMapper.parseObject(inputObject.mockVO(), User.class);
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
        List<User> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), User.class);
        User outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals(Role.STUDENT, outputZero.getRole());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        User outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals(Role.STUDENT, outputSeven.getRole());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        User outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Email Test12", outputTwelve.getEmail());
        assertEquals("Phone Test12", outputTwelve.getPhone());
        assertEquals(Role.STUDENT, outputTwelve.getRole());
        assertTrue((new Date().getTime() - outputTwelve.getCreationDate().getTime()) < 1000);
    }
}
