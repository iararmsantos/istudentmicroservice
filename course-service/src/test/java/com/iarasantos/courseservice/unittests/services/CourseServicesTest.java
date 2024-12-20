package com.iarasantos.courseservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.courseservice.controller.CourseController;
import com.iarasantos.courseservice.data.vo.v1.CourseVO;
import com.iarasantos.courseservice.exception.RequiredObjectIsNullException;
import com.iarasantos.courseservice.unittests.mocks.MockCourse;
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
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CourseServicesTest {

    MockCourse input;

    @InjectMocks
    private CourseServiceImpl service;

    @Mock
    CourseRepository repository;

    @Mock
    private PagedResourcesAssembler<CourseVO> assembler;

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
    void testGetCourses() {
        // Mock Pageable
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));

        // Mock Course entity list and Page
        List<Course> students = input.mockEntityList();
        Page<Course> studentPage = new PageImpl<>(List.of(students.get(0), students.get(1)), pageable, 2);

        // Mock CourseVO
        List<CourseVO> studentVOS = input.mockVOList();

        // Mock repository behavior
        when(repository.findAll(pageable)).thenReturn(studentPage);

        // Mock adding links
        studentVOS.get(0).add(linkTo(methodOn(CourseController.class).getCourse(studentVOS.get(0).getKey())).withSelfRel());
        studentVOS.get(1).add(linkTo(methodOn(CourseController.class).getCourse(studentVOS.get(1).getKey())).withSelfRel());

        // Mock PagedResourcesAssembler behavior
        EntityModel<CourseVO> entityModel1 = EntityModel.of(studentVOS.get(0));
        EntityModel<CourseVO> entityModel2 = EntityModel.of(studentVOS.get(1));
        PagedModel<EntityModel<CourseVO>> expectedPagedModel = PagedModel.of(
                List.of(entityModel1, entityModel2),
                new PagedModel.PageMetadata(10, 0, 2)
        );
        Link link = linkTo(methodOn(CourseController.class).getCourses(0, 10, "asc")).withSelfRel();
        when(assembler.toModel(any(Page.class), eq(link))).thenReturn(expectedPagedModel);

        // Execute the service method
        PagedModel<EntityModel<CourseVO>> result = service.getCourses(pageable);

        // Verify repository interaction
        verify(repository, times(1)).findAll(pageable);

        // Verify PagedResourcesAssembler interaction
        verify(assembler, times(1)).toModel(any(Page.class), eq(link));

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getTitle().equals(studentVOS.get(0).getTitle())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getTitle().equals(studentVOS.get(1).getTitle())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(studentVOS.get(0).getLinks())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(studentVOS.get(1).getLinks())));
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
