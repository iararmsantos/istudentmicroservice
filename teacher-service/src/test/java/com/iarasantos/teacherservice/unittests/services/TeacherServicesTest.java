package com.iarasantos.teacherservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TeacherServicesTest {

    MockTeacher input;

    @InjectMocks
    private TeacherServiceImpl service;

    @Mock
    TeacherRepository repository;


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
    void testGetTeachers(){
        List<Teacher> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.getTeachers();

        assertNotNull(result);
        assertEquals(14, result.size());

        var TeacherOne = result.get(1);
        assertNotNull(TeacherOne);
        assertNotNull(TeacherOne.getKey());
        assertNotNull(TeacherOne.getLinks());
        assertTrue(TeacherOne.getLinks().toString().contains("</api/teachers/1>;rel=\"self\""));
        assertEquals("First Name Test1", TeacherOne.getFirstName());
        assertEquals("Last Name Test1", TeacherOne.getLastName());
        assertEquals("Phone Test1", TeacherOne.getPhone());
        assertEquals("Email Test1", TeacherOne.getEmail());
        assertEquals(Role.TEACHER, TeacherOne.getRole());

        var TeacherFour = result.get(1);
        assertNotNull(TeacherFour);
        assertNotNull(TeacherFour.getKey());
        assertNotNull(TeacherFour.getLinks());
        assertTrue(TeacherFour.getLinks().toString().contains("</api/teachers/1>;rel=\"self\""));
        assertEquals("First Name Test1", TeacherFour.getFirstName());
        assertEquals("Last Name Test1", TeacherFour.getLastName());
        assertEquals("Phone Test1", TeacherFour.getPhone());
        assertEquals("Email Test1", TeacherFour.getEmail());
        assertEquals(Role.TEACHER, TeacherFour.getRole());

        var TeacherSeven = result.get(1);
        assertNotNull(TeacherSeven);
        assertNotNull(TeacherSeven.getKey());
        assertNotNull(TeacherSeven.getLinks());
        assertTrue(TeacherSeven.getLinks().toString().contains("</api/teachers/1>;rel=\"self\""));
        assertEquals("First Name Test1", TeacherSeven.getFirstName());
        assertEquals("Last Name Test1", TeacherSeven.getLastName());
        assertEquals("Phone Test1", TeacherSeven.getPhone());
        assertEquals("Email Test1", TeacherSeven.getEmail());
        assertEquals(Role.TEACHER, TeacherSeven.getRole());
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
