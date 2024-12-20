package com.iarasantos.loginservice.unittests.services;

import com.iarasantos.loginservice.controller.UserController;
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
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @Mock
    private PagedResourcesAssembler<UserResponse> assembler;

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
    void testGetStudents() {
        // Mock Pageable
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));

        // Mock Student entity list and Page
        List<UserEntity> userEntities = input.mockEntityList();
        Page<UserEntity> userEntityPage = new PageImpl<>(List.of(userEntities.get(0), userEntities.get(1)), pageable, 2);

        // Mock StudentVO
        List<UserResponse> userResponseList = input.mockResponseList();

        // Mock repository behavior
        when(repository.findAll(pageable)).thenReturn(userEntityPage);
        when(modelMapper.map(userEntities.get(0), UserResponse.class)).thenReturn(userResponseList.get(0));
        when(modelMapper.map(userEntities.get(1), UserResponse.class)).thenReturn(userResponseList.get(1));

        // Mock adding links
        userResponseList.get(0).add(linkTo(methodOn(UserController.class).getUser(userResponseList.get(0).getUserId())).withSelfRel());
        userResponseList.get(1).add(linkTo(methodOn(UserController.class).getUser(userResponseList.get(1).getUserId())).withSelfRel());

        // Mock PagedResourcesAssembler behavior
        EntityModel<UserResponse> entityModel1 = EntityModel.of(userResponseList.get(0));
        EntityModel<UserResponse> entityModel2 = EntityModel.of(userResponseList.get(1));
        PagedModel<EntityModel<UserResponse>> expectedPagedModel = PagedModel.of(
                List.of(entityModel1, entityModel2),
                new PagedModel.PageMetadata(10, 0, 2)
        );
        Link link = linkTo(methodOn(UserController.class).getUsers(0, 10, "asc")).withSelfRel();
        when(assembler.toModel(any(Page.class), eq(link))).thenReturn(expectedPagedModel);

        // Execute the service method
        PagedModel<EntityModel<UserResponse>> result = service.getUsers(pageable);

        // Verify repository interaction
        verify(repository, times(1)).findAll(pageable);

        // Verify PagedResourcesAssembler interaction
        verify(assembler, times(1)).toModel(any(Page.class), eq(link));

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getFirstName().equals(userResponseList.get(0).getFirstName())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getFirstName().equals(userResponseList.get(1).getFirstName())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(userResponseList.get(0).getLinks())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(userResponseList.get(1).getLinks())));
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
