package com.iarasantos.teacherservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.teacherservice.controller.TeacherController;
import com.iarasantos.teacherservice.data.vo.v1.TeacherVO;
import com.iarasantos.teacherservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.teacherservice.unittests.mocks.MockTeacher;
import com.iarasantos.teacherservice.model.Role;
import com.iarasantos.teacherservice.model.Teacher;
import com.iarasantos.teacherservice.repository.TeacherRepository;
import com.iarasantos.teacherservice.service.TeacherServiceImpl;
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
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TeacherServicesTest {

    MockTeacher input;

    @InjectMocks
    private TeacherServiceImpl service;

    @Mock
    TeacherRepository repository;

    @Mock
    private PagedResourcesAssembler<TeacherVO> assembler;


    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockTeacher();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeacher(){
        //before call repository
        Teacher entity = input.mockEntity(1);
        //after call repository
        Teacher persisted = entity;
        persisted.setId(1L);
        TeacherVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.save(any(Teacher.class))).thenReturn(persisted);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/teachers/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.TEACHER, result.getRole());
    }

    @Test
    void testCreateWithNullTeacher(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullTeacher(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.updateTeacher(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetTeachers() {
        // Mock Pageable
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));

        // Mock Teacher entity list and Page
        List<Teacher> students = input.mockEntityList();
        Page<Teacher> studentPage = new PageImpl<>(List.of(students.get(0), students.get(1)), pageable, 2);

        // Mock TeacherVO
        List<TeacherVO> studentVOS = input.mockVOList();

        // Mock repository behavior
        when(repository.findAll(pageable)).thenReturn(studentPage);

        // Mock adding links
        studentVOS.get(0).add(linkTo(methodOn(TeacherController.class).getTeacher(studentVOS.get(0).getKey())).withSelfRel());
        studentVOS.get(1).add(linkTo(methodOn(TeacherController.class).getTeacher(studentVOS.get(1).getKey())).withSelfRel());

        // Mock PagedResourcesAssembler behavior
        EntityModel<TeacherVO> entityModel1 = EntityModel.of(studentVOS.get(0));
        EntityModel<TeacherVO> entityModel2 = EntityModel.of(studentVOS.get(1));
        PagedModel<EntityModel<TeacherVO>> expectedPagedModel = PagedModel.of(
                List.of(entityModel1, entityModel2),
                new PagedModel.PageMetadata(10, 0, 2)
        );
        Link link = linkTo(methodOn(TeacherController.class).getTeachers(0, 10, "asc")).withSelfRel();
        when(assembler.toModel(any(Page.class), eq(link))).thenReturn(expectedPagedModel);

        // Execute the service method
        PagedModel<EntityModel<TeacherVO>> result = service.getTeachers(pageable);

        // Verify repository interaction
        verify(repository, times(1)).findAll(pageable);

        // Verify PagedResourcesAssembler interaction
        verify(assembler, times(1)).toModel(any(Page.class), eq(link));

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getFirstName().equals(studentVOS.get(0).getFirstName())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getFirstName().equals(studentVOS.get(1).getFirstName())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(studentVOS.get(0).getLinks())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(studentVOS.get(1).getLinks())));
    }

    @Test
    void testGetTeacherById(){
        Teacher entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.getTeacherById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/teachers/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.TEACHER, result.getRole());
    }

    @Test
    void testUpdateTeacher(){
        //before call repository
        Teacher entity = input.mockEntity(1);
        entity.setId(1L);
        //after call repository
        Teacher persisted = entity;
        persisted.setId(1L);
        TeacherVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateTeacher(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/teachers/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.TEACHER, result.getRole());
    }

    @Test
    void testDeleteTeachers(){
        Teacher entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.deleteTeacher(1L);
    }
}
