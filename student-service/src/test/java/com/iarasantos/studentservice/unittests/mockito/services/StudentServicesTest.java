package com.iarasantos.studentservice.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.iarasantos.studentservice.controller.StudentController;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.model.StudentParent;
import com.iarasantos.studentservice.repository.StudentParentsRepository;
import com.iarasantos.studentservice.repository.StudentRepository;
import com.iarasantos.studentservice.service.StudentParentsService;
import com.iarasantos.studentservice.service.StudentServiceImpl;
import com.iarasantos.studentservice.unittests.mocks.MockStudent;
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
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Optional;

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

    @Mock
    private StudentParentsService studentParentsService;

    @Mock
    private PagedResourcesAssembler<StudentVO> assembler;

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
        when(studentParentsService.createParents(any(StudentVO.class), anyLong()))
                .thenReturn(entity.getStudentParents());

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
    void testGetStudents() {
        // Mock Pageable
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));

        // Mock Student entity list and Page
        List<Student> students = input.mockEntityList();
        Page<Student> studentPage = new PageImpl<>(List.of(students.get(0), students.get(1)), pageable, 2);

        // Mock StudentVO
        List<StudentVO> studentVOS = input.mockVOList();

        // Mock repository behavior
        when(repository.findAll(pageable)).thenReturn(studentPage);

        // Mock adding links
        studentVOS.get(0).add(linkTo(methodOn(StudentController.class).getStudent(studentVOS.get(0).getKey())).withSelfRel());
        studentVOS.get(1).add(linkTo(methodOn(StudentController.class).getStudent(studentVOS.get(1).getKey())).withSelfRel());

        // Mock PagedResourcesAssembler behavior
        EntityModel<StudentVO> entityModel1 = EntityModel.of(studentVOS.get(0));
        EntityModel<StudentVO> entityModel2 = EntityModel.of(studentVOS.get(1));
        PagedModel<EntityModel<StudentVO>> expectedPagedModel = PagedModel.of(
                List.of(entityModel1, entityModel2),
                new PagedModel.PageMetadata(10, 0, 2)
        );
        Link link = linkTo(methodOn(StudentController.class).getStudents(0, 10, "asc")).withSelfRel();
        when(assembler.toModel(any(Page.class), eq(link))).thenReturn(expectedPagedModel);

        // Execute the service method
        PagedModel<EntityModel<StudentVO>> result = service.getStudents(pageable);

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
