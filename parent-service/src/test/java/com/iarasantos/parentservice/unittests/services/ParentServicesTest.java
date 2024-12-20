package com.iarasantos.parentservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.parentservice.controller.ParentController;
import com.iarasantos.parentservice.data.vo.v1.ParentVO;
import com.iarasantos.parentservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.parentservice.unittests.mocks.MockParent;
import com.iarasantos.parentservice.model.Parent;
import com.iarasantos.parentservice.model.Role;
import com.iarasantos.parentservice.repository.ParentRepository;
import com.iarasantos.parentservice.service.ParentServiceImpl;
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
public class ParentServicesTest {

    MockParent input;

    @InjectMocks
    private ParentServiceImpl service;

    @Mock
    ParentRepository repository;

    @Mock
    private PagedResourcesAssembler<ParentVO> assembler;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockParent();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateParent(){
        //before call repository
        Parent entity = input.mockEntity(1);
        //after call repository
        Parent persisted = entity;
        persisted.setId(1L);
        ParentVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.save(any(Parent.class))).thenReturn(persisted);

        var result = service.createParent(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/parents/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.PARENT, result.getRole());
    }

    @Test
    void testCreateWithNullParent(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.createParent(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullParent(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.updateParent(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetParents() {
        // Mock Pageable
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));

        // Mock Parent entity list and Page
        List<Parent> parents = input.mockEntityList();
        Page<Parent> parentPage = new PageImpl<>(List.of(parents.get(0), parents.get(1)), pageable, 2);

        // Mock ParentVO
        List<ParentVO> parentVOS = input.mockVOList();

        // Mock repository behavior
        when(repository.findAll(pageable)).thenReturn(parentPage);

        // Mock adding links
        parentVOS.get(0).add(linkTo(methodOn(ParentController.class).getParent(parentVOS.get(0).getKey())).withSelfRel());
        parentVOS.get(1).add(linkTo(methodOn(ParentController.class).getParent(parentVOS.get(1).getKey())).withSelfRel());

        // Mock PagedResourcesAssembler behavior
        EntityModel<ParentVO> entityModel1 = EntityModel.of(parentVOS.get(0));
        EntityModel<ParentVO> entityModel2 = EntityModel.of(parentVOS.get(1));
        PagedModel<EntityModel<ParentVO>> expectedPagedModel = PagedModel.of(
                List.of(entityModel1, entityModel2),
                new PagedModel.PageMetadata(10, 0, 2)
        );
        Link link = linkTo(methodOn(ParentController.class).getParents(0, 10, "asc")).withSelfRel();
        when(assembler.toModel(any(Page.class), eq(link))).thenReturn(expectedPagedModel);

        // Execute the service method
        PagedModel<EntityModel<ParentVO>> result = service.getParents(pageable);

        // Verify repository interaction
        verify(repository, times(1)).findAll(pageable);

        // Verify PagedResourcesAssembler interaction
        verify(assembler, times(1)).toModel(any(Page.class), eq(link));

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getFirstName().equals(parentVOS.get(0).getFirstName())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getFirstName().equals(parentVOS.get(1).getFirstName())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(parentVOS.get(0).getLinks())));
        assertTrue(result.getContent().stream().anyMatch(e -> e.getContent().getLinks().equals(parentVOS.get(1).getLinks())));
    }

    @Test
    void testGetParentById(){
        Parent entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.getParentById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result.getLinks().toString());
        assertTrue(result.getLinks().toString().contains("</api/parents/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.PARENT, result.getRole());
    }

    @Test
    void testUpdateParent(){
        //before call repository
        Parent entity = input.mockEntity(1);
        entity.setId(1L);
        //after call repository
        Parent persisted = entity;
        persisted.setId(1L);
        ParentVO vo = input.mockVO(1);
        vo.setKey(1L);
//        when(repository.save(entity)).thenReturn(persisted);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateParent(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.getLinks().toString().contains("</api/parents/1>;rel=\"self\""));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Phone Test1", result.getPhone());
        assertEquals("Email Test1", result.getEmail());
        assertEquals(Role.PARENT, result.getRole());
    }

    @Test
    void testDeleteParents(){
        Parent entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.deleteParent(1L);
    }
}
