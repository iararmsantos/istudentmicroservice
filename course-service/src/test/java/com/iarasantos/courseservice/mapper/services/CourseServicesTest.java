package com.iarasantos.courseservice.mapper.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.exception.RequiredObjectIsNullException;
import com.iarasantos.courseservice.mapper.mocks.MockCourse;
import com.iarasantos.courseservice.model.Course;
import com.iarasantos.courseservice.model.Season;
import com.iarasantos.courseservice.repository.CourseRepository;
import com.iarasantos.courseservice.service.CourseServiceImpl;
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
public class CourseServicesTest {

    MockCourse input;

    @InjectMocks
    private CourseServiceImpl service;

    @Mock
    CourseRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockCourse();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse(){
        //before call repository
        Course entity = input.mockEntity(1);
        //after call repository
        Course persisted = entity;
        persisted.setId(1L);
        CourseVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.save(any(Course.class))).thenReturn(persisted);

        var result = service.createCourse(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/courses/1>;rel=\"self\""));
        assertEquals("Title Test1", result.getTitle());
        assertEquals(Season.FALL, result.getSection());
        assertEquals(1, result.getYear());
        assertEquals(Long.valueOf(1), result.getTeacherId());
        assertTrue((new Date().getTime() - result.getCreationDate().getTime()) < 1000);
    }

    @Test
    void testCreateWithNullCourse(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.createCourse(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullCourse(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.updateCourse(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetCourses(){
        List<Course> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.getCourses();

        assertNotNull(result);
        assertEquals(14, result.size());

        var courseOne = result.get(1);
        assertNotNull(courseOne);
        assertNotNull(courseOne.getKey());
        assertNotNull(courseOne.getLinks());
        assertTrue(courseOne.getLinks().toString().contains("</api/courses/1>;rel=\"self\""));
        assertEquals("Title Test1", courseOne.getTitle());
        assertEquals(Season.FALL, courseOne.getSection());
        assertEquals(1, courseOne.getYear());
        assertEquals(Long.valueOf(1), courseOne.getTeacherId());
        assertTrue((new Date().getTime() - courseOne.getCreationDate().getTime()) < 1000);

        var courseFour = result.get(4);
        assertNotNull(courseFour);
        assertNotNull(courseFour.getKey());
        assertNotNull(courseFour.getLinks());
        assertTrue(courseFour.getLinks().toString().contains("</api/courses/4>;rel=\"self\""));
        assertEquals("Title Test4", courseFour.getTitle());
        assertEquals(Season.FALL, courseFour.getSection());
        assertEquals(4, courseFour.getYear());
        assertEquals(Long.valueOf(4), courseFour.getTeacherId());
        assertTrue((new Date().getTime() - courseFour.getCreationDate().getTime()) < 1000);

        var courseSeven = result.get(7);
        assertNotNull(courseSeven);
        assertNotNull(courseSeven.getKey());
        assertNotNull(courseSeven.getLinks());
        assertTrue(courseSeven.getLinks().toString().contains("</api/courses/7>;rel=\"self\""));
        assertEquals("Title Test7", courseSeven.getTitle());
        assertEquals(Season.FALL, courseSeven.getSection());
        assertEquals(7, courseSeven.getYear());
        assertEquals(Long.valueOf(7), courseSeven.getTeacherId());
        assertTrue((new Date().getTime() - courseSeven.getCreationDate().getTime()) < 1000);
    }

    @Test
    void testGetCourseById(){
        Course entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.getCourseById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/courses/1>;rel=\"self\""));
        assertEquals("Title Test1", result.getTitle());
        assertEquals(Season.FALL, result.getSection());
        assertEquals(1, result.getYear());
        assertEquals(Long.valueOf(1), result.getTeacherId());
        assertTrue((new Date().getTime() - result.getCreationDate().getTime()) < 1000);
    }

    @Test
    void testUpdateCourse(){
        //before call repository
        Course entity = input.mockEntity(1);
        entity.setId(1L);
        //after call repository
        Course persisted = entity;
        persisted.setId(1L);
        CourseVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateCourse(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/courses/1>;rel=\"self\""));
        assertEquals("Title Test1", result.getTitle());
        assertEquals(Season.FALL, result.getSection());
        assertEquals(1, result.getYear());
        assertEquals(Long.valueOf(1), result.getTeacherId());
        assertTrue((new Date().getTime() - result.getCreationDate().getTime()) < 1000);
    }

    @Test
    void testDeleteCourses(){
        Course entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.deleteCourse(1L);
    }
}
