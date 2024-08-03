package com.iarasantos.loginservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.loginservice.controller.UserController;
import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.data.vo.v1.UserResponse;
import com.iarasantos.loginservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.loginservice.unittests.mocks.MockUser;
import com.iarasantos.loginservice.model.Role;
import com.iarasantos.loginservice.model.UserEntity;
import com.iarasantos.loginservice.repository.UserRepository;
import com.iarasantos.loginservice.service.UserServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserEntityServicesTest {

    MockUser input;

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    UserRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUpMocks() throws Exception {
        MockitoAnnotations.openMocks(this);
        input = new MockUser();
    }

    @Test
    void testCreateUser(){
        UserEntity mockEntity = input.mockEntity(1);

        UserEntity persistedEntity = input.mockEntity(1);
        persistedEntity.setUserId(UUID.randomUUID().toString());
        persistedEntity.setPassword(UUID.randomUUID().toString()); // Simulate encoded password
        persistedEntity.setCreationDate(new Date());

        UserRequest mockRequest = input.mockVO(1);

        UserResponse mockResponse = new UserResponse();
        mockResponse.setFirstName("First Name Test1");
        mockResponse.setLastName("Last Name Test1");
        mockResponse.setPhone("Phone Test1");
        mockResponse.setEmail("Email Test1");
        mockResponse.setRole(Role.STUDENT);
        mockResponse.setUserId(persistedEntity.getUserId());
        Link selfLink = linkTo(methodOn(UserController.class).getUser(persistedEntity.getUserId())).withSelfRel();
        mockResponse.add(selfLink);

        // Set up mock behavior
        when(modelMapper.map(mockRequest, UserEntity.class)).thenReturn(mockEntity);
        when(bCryptPasswordEncoder.encode(mockRequest.getPassword())).thenReturn(persistedEntity.getPassword());
        when(repository.save(any(UserEntity.class))).thenReturn(persistedEntity);
        when(modelMapper.map(persistedEntity, UserResponse.class)).thenReturn(mockResponse);

        // Act: Call the method under test
        UserResponse result = service.createUser(mockRequest);

        // Assert: Verify the results
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().contains(selfLink));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }


    @Test
    void testCreateWithNullUser(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.createUser(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullUser(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.updateUser(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetUsers(){
        List<UserEntity> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.getUsers();

        assertNotNull(result);
        assertEquals(14, result.size());

        var UserOne = result.get(1);
        assertNotNull(UserOne);
        assertNotNull(UserOne.getLinks());
        System.out.println(UserOne.getLinks().toString());
        assertTrue(UserOne.getLinks().toString().contains("</api/users/1>;rel=\"self\""));
        assertEquals("First Name Test1", UserOne.getFirstName());
        assertEquals("Last Name Test1", UserOne.getLastName());
        assertEquals("Phone Test1", UserOne.getPhone());
        assertEquals("Email Test1", UserOne.getEmail());
        assertEquals(Role.STUDENT, UserOne.getRole());

        var UserFour = result.get(1);
        assertNotNull(UserFour);
        assertNotNull(UserFour.getLinks());
        assertTrue(UserFour.getLinks().toString().contains("</api/users/1>;rel=\"self\""));
        assertEquals("First Name Test1", UserFour.getFirstName());
        assertEquals("Last Name Test1", UserFour.getLastName());
        assertEquals("Phone Test1", UserFour.getPhone());
        assertEquals("Email Test1", UserFour.getEmail());
        assertEquals(Role.STUDENT, UserFour.getRole());

        var UserSeven = result.get(1);
        assertNotNull(UserSeven);
        assertNotNull(UserSeven.getLinks());
        assertTrue(UserSeven.getLinks().toString().contains("</api/users/1>;rel=\"self\""));
        assertEquals("First Name Test1", UserSeven.getFirstName());
        assertEquals("Last Name Test1", UserSeven.getLastName());
        assertEquals("Phone Test1", UserSeven.getPhone());
        assertEquals("Email Test1", UserSeven.getEmail());
        assertEquals(Role.STUDENT, UserSeven.getRole());
    }

    @Test
    void testGetUserById(){
        UserEntity entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.getUser(1L);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/users/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testUpdateUser(){
        //before call repository
        UserEntity entity = input.mockEntity(1);
        entity.setId(1L);
        //after call repository
        UserEntity persisted = entity;
        persisted.setId(1L);
        UserRequest vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateUser(vo);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/users/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testDeleteUsers(){
        UserEntity entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.deleteUser(1L);
    }
}
