package com.iarasantos.gradeservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.gradeservice.controller.GradeController;
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
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class GradeServicesTest {

    MockGrade input;

    @InjectMocks
    private
    GradeServiceImpl service;

    @Mock
    GradeRepository repository;

    @Mock
    private PagedResourcesAssembler<GradeVO> assembler;

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
    void testGetGrades() {
        // Mock Pageable
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));

        // Mock Grade entity list and Page
        List<Grade> students = input.mockEntityList();
        Page<Grade> studentPage = new PageImpl<>(List.of(students.get(0), students.get(1)), pageable, 2);

        // Mock GradeVO
        List<GradeVO> studentVOS = input.mockVOList();

        // Mock repository behavior
        when(repository.findAll(pageable)).thenReturn(studentPage);

        // Mock adding links
        studentVOS.get(0).add(linkTo(methodOn(GradeController.class).getGrade(studentVOS.get(0).getKey())).withSelfRel());
        studentVOS.get(1).add(linkTo(methodOn(GradeController.class).getGrade(studentVOS.get(1).getKey())).withSelfRel());

        // Mock PagedResourcesAssembler behavior
        EntityModel<GradeVO> entityModel1 = EntityModel.of(studentVOS.get(0));
        EntityModel<GradeVO> entityModel2 = EntityModel.of(studentVOS.get(1));
        PagedModel<EntityModel<GradeVO>> expectedPagedModel = PagedModel.of(
                List.of(entityModel1, entityModel2),
                new PagedModel.PageMetadata(10, 0, 2)
        );
        Link link = linkTo(methodOn(GradeController.class).getGrades(0, 10, "asc")).withSelfRel();
        when(assembler.toModel(any(Page.class), eq(link))).thenReturn(expectedPagedModel);

        // Execute the service method
        PagedModel<EntityModel<GradeVO>> result = service.getGrades(pageable);

        // Verify repository interaction
        verify(repository, times(1)).findAll(pageable);

        // Verify PagedResourcesAssembler interaction
        verify(assembler, times(1)).toModel(any(Page.class), eq(link));

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLetterGrade().equals(studentVOS.get(0).getLetterGrade())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLetterGrade().equals(studentVOS.get(1).getLetterGrade())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(studentVOS.get(0).getLinks())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(studentVOS.get(1).getLinks())));
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
