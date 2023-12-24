package com.iarasantos.parentservice.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ParentServicesTest {

    MockParent input;

    @InjectMocks
    private ParentServiceImpl service;

    @Mock
    ParentRepository repository;

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
    void testGetParents(){
        List<Parent> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.getParents();

        assertNotNull(result);
        assertEquals(14, result.size());

        var ParentOne = result.get(1);
        assertNotNull(ParentOne);
        assertNotNull(ParentOne.getKey());
        assertNotNull(ParentOne.getLinks());
        assertTrue(ParentOne.getLinks().toString().contains("</api/parents/1>;rel=\"self\""));
        assertEquals("First Name Test1", ParentOne.getFirstName());
        assertEquals("Last Name Test1", ParentOne.getLastName());
        assertEquals("Phone Test1", ParentOne.getPhone());
        assertEquals("Email Test1", ParentOne.getEmail());
        assertEquals(Role.PARENT, ParentOne.getRole());

        var ParentFour = result.get(1);
        assertNotNull(ParentFour);
        assertNotNull(ParentFour.getKey());
        assertNotNull(ParentFour.getLinks());
        assertTrue(ParentFour.getLinks().toString().contains("</api/parents/1>;rel=\"self\""));
        assertEquals("First Name Test1", ParentFour.getFirstName());
        assertEquals("Last Name Test1", ParentFour.getLastName());
        assertEquals("Phone Test1", ParentFour.getPhone());
        assertEquals("Email Test1", ParentFour.getEmail());
        assertEquals(Role.PARENT, ParentFour.getRole());

        var ParentSeven = result.get(1);
        assertNotNull(ParentSeven);
        assertNotNull(ParentSeven.getKey());
        assertNotNull(ParentSeven.getLinks());
        assertTrue(ParentSeven.getLinks().toString().contains("</api/parents/1>;rel=\"self\""));
        assertEquals("First Name Test1", ParentSeven.getFirstName());
        assertEquals("Last Name Test1", ParentSeven.getLastName());
        assertEquals("Phone Test1", ParentSeven.getPhone());
        assertEquals("Email Test1", ParentSeven.getEmail());
        assertEquals(Role.PARENT, ParentSeven.getRole());
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
