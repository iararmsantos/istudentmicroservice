package com.iarasantos.studentservice.unittests.mockito.services;

import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.model.StudentParent;
import com.iarasantos.studentservice.repository.StudentParentsRepository;
import com.iarasantos.studentservice.repository.StudentRepository;
import com.iarasantos.studentservice.service.StudentServiceImpl;
import com.iarasantos.studentservice.unittests.mocks.MockStudent;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class StudentServicesTest {

    MockStudent input;

    @InjectMocks
    private StudentServiceImpl service;

    @Mock
    StudentRepository repository;

    @Mock
    StudentParentsRepository parentsRepository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockStudent();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent(){
        //before call repository
        Student entity = input.mockEntity(1);
        //after call repository
        Student persisted = entity;
        persisted.setId(1L);
        StudentVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.save(any(Student.class))).thenReturn(persisted);

        var result = service.createStudent(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/students/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testCreateWithNullStudent(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.createStudent(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullStudent(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.updateStudent(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetStudents(){
        List<Student> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.getStudents();

        assertNotNull(result);
        assertEquals(14, result.size());

        var studentOne = result.get(1);
        assertNotNull(studentOne);
        assertNotNull(studentOne.getKey());
        assertNotNull(studentOne.getLinks());
        assertTrue(studentOne.getLinks().toString().contains("</api/students/1>;rel=\"self\""));
        assertEquals(Long.valueOf(1), studentOne.getKey());
        assertEquals("First Name Test1", studentOne.getFirstName());
        assertEquals("Last Name Test1", studentOne.getLastName());
        assertEquals("Phone Test1", studentOne.getPhone());
        assertEquals("Email Test1", studentOne.getEmail());
        assertEquals(Role.STUDENT, studentOne.getRole());

        var studentFour = result.get(4);
        assertNotNull(studentFour);
        assertNotNull(studentFour.getKey());
        assertNotNull(studentFour.getLinks());
        assertTrue(studentFour.getLinks().toString().contains("</api/students/4>;rel=\"self\""));
        assertEquals(Long.valueOf(4), studentFour.getKey());
        assertEquals("First Name Test4", studentFour.getFirstName());
        assertEquals("Last Name Test4", studentFour.getLastName());
        assertEquals("Phone Test4", studentFour.getPhone());
        assertEquals("Email Test4", studentFour.getEmail());
        assertEquals(Role.STUDENT, studentFour.getRole());

        var studentSeven = result.get(7);
        assertNotNull(studentSeven);
        assertNotNull(studentSeven.getKey());
        assertNotNull(studentSeven.getLinks());
        assertTrue(studentSeven.getLinks().toString().contains("</api/students/7>;rel=\"self\""));
        assertEquals(Long.valueOf(7), studentSeven.getKey());
        assertEquals("First Name Test7", studentSeven.getFirstName());
        assertEquals("Last Name Test7", studentSeven.getLastName());
        assertEquals("Phone Test7", studentSeven.getPhone());
        assertEquals("Email Test7", studentSeven.getEmail());
        assertEquals(Role.STUDENT, studentSeven.getRole());
    }

    @Test
    void testGetStudentById(){
        Student entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.getStudentById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/students/1>;rel=\"self\""));
        assertEquals(Long.valueOf(1), result.getKey());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testUpdateStudent(){
        //before call repository
        Student entity = input.mockEntity(1);
        entity.setId(1L);
        //after call repository
        Student persisted = entity;
        persisted.setId(1L);
        StudentVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateStudent(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/students/1>;rel=\"self\""));
        assertEquals(Long.valueOf(1), result.getKey());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testDeleteStudents(){
        Student entity = input.mockEntity(1);
        entity.setId(1L);
        StudentParent parent = mockParent(1);
        parent.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        lenient().when(parentsRepository.findById(1L)).thenReturn(Optional.of(parent));
        service.deleteStudent(1L);
    }

    public StudentParent mockParent(Integer number) {
        StudentParent studentParent = new StudentParent();
        studentParent.setId(number.longValue());
        studentParent.setParentId(number.longValue());
        studentParent.setStudentId(number.longValue());

        return studentParent;
    }
}
