package com.iarasantos.gradeservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.exception.RequiredObjectIsNullException;
import com.iarasantos.gradeservice.unittests.mocks.MockGrade;
import com.iarasantos.gradeservice.model.Grade;
import com.iarasantos.gradeservice.repository.GradeRepository;
import com.iarasantos.gradeservice.service.GradeServiceImpl;
import java.util.Date;
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
public class GradeServicesTest {

    MockGrade input;

    @InjectMocks
    private
    GradeServiceImpl service;

    @Mock
    GradeRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockGrade();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGrade(){
        //before call repository
        Grade entity = input.mockEntity(1);
        //after call repository
        Grade persisted = entity;
        persisted.setId(1L);
        GradeVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.save(any(Grade.class))).thenReturn(persisted);

        var result = service.createGrade(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/grades/1>;rel=\"self\""));
        assertEquals(Long.valueOf(1), result.getKey());
        assertEquals("A", result.getLetterGrade());
        assertEquals(Double.valueOf(1), result.getNumberGrade());
        assertEquals(Long.valueOf(1), result.getStudentId());
        assertEquals(Long.valueOf(1), result.getCourseId());

        assertTrue((new Date().getTime() - result.getCreationDate().getTime()) < 1000);
    }

    @Test
    void testCreateWithNullGrade(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.createGrade(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullGrade(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.updateGrade(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetGrades(){
        List<Grade> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.getGrades();

        assertNotNull(result);
        assertEquals(14, result.size());

        var gradeOne = result.get(1);
        assertNotNull(gradeOne);
        assertNotNull(gradeOne.getKey());
        assertNotNull(gradeOne.getLinks());
        assertTrue(gradeOne.getLinks().toString().contains("</api/grades/1>;rel=\"self\""));
        assertEquals(Long.valueOf(1), gradeOne.getKey());
        assertEquals("A", gradeOne.getLetterGrade());
        assertEquals(Double.valueOf(1), gradeOne.getNumberGrade());
        assertEquals(Long.valueOf(1), gradeOne.getStudentId());
        assertEquals(Long.valueOf(1), gradeOne.getCourseId());

        assertTrue((new Date().getTime() - gradeOne.getCreationDate().getTime()) < 1000);

        var gradeFour = result.get(4);
        assertNotNull(gradeFour);
        assertNotNull(gradeFour.getKey());
        assertNotNull(gradeFour.getLinks());
        assertTrue(gradeFour.getLinks().toString().contains("</api/grades/4>;rel=\"self\""));
        assertEquals(Long.valueOf(4), gradeFour.getKey());
        assertEquals("A", gradeFour.getLetterGrade());
        assertEquals(Double.valueOf(4), gradeFour.getNumberGrade());
        assertEquals(Long.valueOf(4), gradeFour.getStudentId());
        assertEquals(Long.valueOf(4), gradeFour.getCourseId());

        assertTrue((new Date().getTime() - gradeFour.getCreationDate().getTime()) < 1000);

        var gradeSeven = result.get(7);
        assertNotNull(gradeSeven);
        assertNotNull(gradeSeven.getKey());
        assertNotNull(gradeSeven.getLinks());
        assertTrue(gradeSeven.getLinks().toString().contains("</api/grades/7>;rel=\"self\""));
        assertEquals(Long.valueOf(7), gradeSeven.getKey());
        assertEquals("A", gradeSeven.getLetterGrade());
        assertEquals(Double.valueOf(7), gradeSeven.getNumberGrade());
        assertEquals(Long.valueOf(7), gradeSeven.getStudentId());
        assertEquals(Long.valueOf(7), gradeSeven.getCourseId());

        assertTrue((new Date().getTime() - gradeSeven.getCreationDate().getTime()) < 1000);
    }

    @Test
    void testGetGradeById(){
        Grade entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.getGradeById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/grades/1>;rel=\"self\""));
        assertEquals(Long.valueOf(1), result.getKey());
        assertEquals("A", result.getLetterGrade());
        assertEquals(Double.valueOf(1), result.getNumberGrade());
        assertEquals(Long.valueOf(1), result.getStudentId());
        assertEquals(Long.valueOf(1), result.getCourseId());

        assertTrue((new Date().getTime() - result.getCreationDate().getTime()) < 1000);
    }

    @Test
    void testUpdateGrade(){
        //before call repository
        Grade entity = input.mockEntity(1);
        entity.setId(1L);
        //after call repository
        Grade persisted = entity;
        persisted.setId(1L);
        GradeVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateGrade(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/grades/1>;rel=\"self\""));
        assertEquals(Long.valueOf(1), result.getKey());
        assertEquals("A", result.getLetterGrade());
        assertEquals(Double.valueOf(1), result.getNumberGrade());
        assertEquals(Long.valueOf(1), result.getStudentId());
        assertEquals(Long.valueOf(1), result.getCourseId());

        assertTrue((new Date().getTime() - result.getCreationDate().getTime()) < 1000);
    }

    @Test
    void testDeleteGrades(){
        Grade entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.deleteGrade(1L);
    }
}
