package com.iarasantos.loginservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.loginservice.unittests.mocks.MockUser;
import com.iarasantos.loginservice.model.Role;
import com.iarasantos.loginservice.model.User;
import com.iarasantos.loginservice.repository.UserRepository;
import com.iarasantos.loginservice.service.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServicesTest {

    MockUser input;

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    UserRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockUser();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser(){
        //before call repository
        User entity = input.mockEntity(1);
        //after call repository
        User persisted = entity;
        persisted.setId(1L);
        UserRequest vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.save(any(User.class))).thenReturn(persisted);

        var result = service.createUser(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/users/1>;rel=\"self\""));
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
        List<User> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.getUsers();

        assertNotNull(result);
        assertEquals(14, result.size());

        var UserOne = result.get(1);
        assertNotNull(UserOne);
        assertNotNull(UserOne.getKey());
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
        assertNotNull(UserFour.getKey());
        assertNotNull(UserFour.getLinks());
        assertTrue(UserFour.getLinks().toString().contains("</api/users/1>;rel=\"self\""));
        assertEquals("First Name Test1", UserFour.getFirstName());
        assertEquals("Last Name Test1", UserFour.getLastName());
        assertEquals("Phone Test1", UserFour.getPhone());
        assertEquals("Email Test1", UserFour.getEmail());
        assertEquals(Role.STUDENT, UserFour.getRole());

        var UserSeven = result.get(1);
        assertNotNull(UserSeven);
        assertNotNull(UserSeven.getKey());
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
        User entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.getUser(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
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
        User entity = input.mockEntity(1);
        entity.setId(1L);
        //after call repository
        User persisted = entity;
        persisted.setId(1L);
        UserRequest vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateUser(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
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
        User entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.deleteUser(1L);
    }
}
