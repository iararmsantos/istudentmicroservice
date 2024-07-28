package com.iarasantos.loginservice.unittests.mapper;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.model.Role;
import com.iarasantos.loginservice.model.User;
import com.iarasantos.loginservice.unittests.mocks.MockUser;
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

    MockUser inputObject;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        inputObject = new MockUser();
        modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Test
    public void parseEntityToVOTest() {
        User user = inputObject.mockEntity();

        UserRequest output = modelMapper.map(user, UserRequest.class);
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
        List<User> users = inputObject.mockEntityList();

        List<UserRequest> outputList = users.stream().map((user) ->
                        modelMapper.map(user, UserRequest.class))
                .collect(Collectors.toList());

        UserRequest outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Email Test0", outputZero.getEmail());
        assertEquals("Phone Test0", outputZero.getPhone());
        assertEquals(Role.STUDENT, outputZero.getRole());
        assertTrue((new Date().getTime() - outputZero.getCreationDate().getTime()) < 1000);

        UserRequest outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Email Test7", outputSeven.getEmail());
        assertEquals("Phone Test7", outputSeven.getPhone());
        assertEquals(Role.STUDENT, outputSeven.getRole());
        assertTrue((new Date().getTime() - outputSeven.getCreationDate().getTime()) < 1000);

        UserRequest outputTwelve = outputList.get(12);

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
        UserRequest user = inputObject.mockVO();
        User output = modelMapper.map(user, User.class);

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
        List<UserRequest> users = inputObject.mockVOList();

        List<User> outputList = users.stream().map((user) ->
                        modelMapper.map(user, User.class))
                .collect(Collectors.toList());

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

    private void propertyMapping() {
        TypeMap<User, UserRequest> propertyMapper = this.modelMapper.createTypeMap(User.class, UserRequest.class);
        propertyMapper.addMapping(User::getId, UserRequest::setKey);
        TypeMap<UserRequest, User> propertyMapper2 = this.modelMapper.createTypeMap(UserRequest.class, User.class);
        propertyMapper2.addMapping(UserRequest::getKey, User::setId);
    }
}
