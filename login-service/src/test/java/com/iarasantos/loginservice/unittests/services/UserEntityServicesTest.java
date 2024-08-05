package com.iarasantos.loginservice.unittests.services;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.data.vo.v1.UserResponse;
import com.iarasantos.loginservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.loginservice.model.Role;
import com.iarasantos.loginservice.model.UserEntity;
import com.iarasantos.loginservice.repository.UserRepository;
import com.iarasantos.loginservice.service.UserServiceImpl;
import com.iarasantos.loginservice.unittests.mocks.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserEntityServicesTest {

    MockUser input;

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    UserRepository repository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockUser();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserRequest userRequest = input.mockVO(1);
        UserEntity userEntity = input.mockEntity(1);
        UserEntity persisted = userEntity;
        persisted.setId(1L);

        UserResponse userResponse = input.mockResponse(1);

        when(modelMapper.map(userRequest, UserEntity.class)).thenReturn(userEntity);
        when(bCryptPasswordEncoder.encode(userRequest.getPassword())).thenReturn(userRequest.getPassword());
        when(modelMapper.map(userEntity, UserResponse.class)).thenReturn(userResponse);
        when(repository.save(any(UserEntity.class))).thenReturn(persisted);

//        then save in the repository
        UserResponse result = service.createUser(userRequest);

        assertNotNull(result);
//        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/users/TestUserId1>;rel=\"self\""));
        assertEquals("TestUserId1", result.getUserId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testCreateWithNullUser() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.createUser(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullUser() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.updateUser(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetUsers() {
        List<UserEntity> users = input.mockEntityList();
        List<UserResponse> userResponses = input.mockResponseList();

        assert users.size() == userResponses.size();

        users.forEach(userEntity -> {
            UserResponse userResponse = userResponses.get(users.indexOf(userEntity));
            when(modelMapper.map(userEntity, UserResponse.class)).thenReturn(userResponse);
        });

        when(repository.findAll()).thenReturn(users);

        var result = service.getUsers();

        assertNotNull(result);
        assertEquals(14, result.size());

        var userOne = result.get(1);
        assertNotNull(userOne);
        assertNotNull(userOne.getLinks());
        System.out.println(userOne.getLinks().toString());
        assertTrue(userOne.getLinks().toString().contains("</api/users/TestUserId1>;rel=\"self\""));
        assertEquals("First Name Test1", userOne.getFirstName());
        assertEquals("Last Name Test1", userOne.getLastName());
        assertEquals("Phone Test1", userOne.getPhone());
        assertEquals("Email Test1", userOne.getEmail());
        assertEquals("TestUserId1", userOne.getUserId());
        assertEquals(Role.STUDENT, userOne.getRole());

        var userFour = result.get(4);
        assertNotNull(userFour);
        assertNotNull(userFour.getLinks());
        assertTrue(userFour.getLinks().toString().contains("</api/users/TestUserId4>;rel=\"self\""));
        assertEquals("First Name Test4", userFour.getFirstName());
        assertEquals("Last Name Test4", userFour.getLastName());
        assertEquals("Phone Test4", userFour.getPhone());
        assertEquals("Email Test4", userFour.getEmail());
        assertEquals("TestUserId4", userFour.getUserId());
        assertEquals(Role.STUDENT, userFour.getRole());

        var userSeven = result.get(7);
        assertNotNull(userSeven);
        assertNotNull(userSeven.getLinks());
        assertTrue(userSeven.getLinks().toString().contains("</api/users/TestUserId7>;rel=\"self\""));
        assertEquals("First Name Test7", userSeven.getFirstName());
        assertEquals("Last Name Test7", userSeven.getLastName());
        assertEquals("Phone Test7", userSeven.getPhone());
        assertEquals("Email Test7", userSeven.getEmail());
        assertEquals("TestUserId7", userSeven.getUserId());
        assertEquals(Role.STUDENT, userSeven.getRole());
    }

    @Test
    void testGetUserById() {
        UserEntity entity = input.mockEntity(1);
        UserResponse userResponse = input.mockResponse(1);
        entity.setId(1L);

        when(modelMapper.map(entity, UserResponse.class)).thenReturn(userResponse);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.getUser(1L);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/users/TestUserId1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testUpdateUser() {
        UserRequest userRequest = input.mockVO(1);
        userRequest.setKey(1L);
        UserEntity userEntity = input.mockEntity(1);
        userEntity.setId(1L);

        UserResponse userResponse = input.mockResponse(1);

        when(modelMapper.map(userEntity, UserResponse.class)).thenReturn(userResponse);
        when(repository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(repository.findById(1L)).thenReturn(Optional.of(userEntity));

        var result = service.updateUser(userRequest);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/users/TestUserId1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testDeleteUsers() {
        UserEntity entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.deleteUser(1L);
    }
}
