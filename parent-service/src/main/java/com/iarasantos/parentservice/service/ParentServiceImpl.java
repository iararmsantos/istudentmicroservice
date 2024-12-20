package com.iarasantos.parentservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.parentservice.controller.ParentController;
import com.iarasantos.parentservice.data.vo.v1.ParentVO;
import com.iarasantos.parentservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.parentservice.exceptions.ResourceNotFoundException;
import com.iarasantos.parentservice.model.Parent;
import com.iarasantos.parentservice.repository.ParentRepository;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {
    @Autowired
    private ParentRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    private PagedResourcesAssembler<ParentVO> assembler;

    public ParentServiceImpl() {
        modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Override
    public PagedModel<EntityModel<ParentVO>> getParents(Pageable pageable) {
        // Fetch paginated Student entities from the repository
        Page<Parent> studentPage = repository.findAll(pageable);

        // Map each Student to a StudentVO
        Page<ParentVO> studentVoPage = studentPage.map(s -> this.modelMapper.map(s, ParentVO.class));

        // Add self-links to each StudentVO
        studentVoPage.forEach(p -> p.add(
                linkTo(methodOn(ParentController.class)
                        .getParent(p.getKey())
                ).withSelfRel()
        ));

        // add pagination hateoas link
        Link link = linkTo(methodOn(ParentController.class).getParents(pageable.getPageNumber(),
                pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(studentVoPage, link);

    }

    @Override
    public ParentVO getParentById(Long parentId) {
        Parent parent = repository.findById(parentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + parentId + " not found!"));
        ParentVO vo = this.modelMapper.map(parent, ParentVO.class);

        vo.add(linkTo(methodOn(ParentController.class).getParent(vo.getKey())).withSelfRel());

        return vo;
    }

    @Override
    public ParentVO createParent(ParentVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }

        Parent parent = this.modelMapper.map(request, Parent.class);
        parent.setCreationDate(new Date());
        ParentVO vo = this.modelMapper.map(repository.save(parent), ParentVO.class);

        vo.add(linkTo(methodOn(ParentController.class).getParent(vo.getKey())).withSelfRel());
        return vo;
    }

    @Override
    public void deleteParent(Long parentId) {
        Parent parent = repository.findById(parentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + parentId + " not found!"));
        repository.deleteById(parent.getId());
    }

    @Override
    public ParentVO updateParent(ParentVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }

        Parent parent = repository.findById(request.getKey()).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + request.getKey() + " not found!"));
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setPhone(request.getPhone());
        parent.setEmail(request.getEmail());
        parent.setRole(request.getRole());

        ParentVO vo = this.modelMapper.map(repository.save(parent), ParentVO.class);

        vo.add(linkTo(methodOn(ParentController.class).getParent(vo.getKey())).withSelfRel());

        return vo;
    }

    private void propertyMapping() {
        TypeMap<Parent, ParentVO> propertyMapper = this.modelMapper.createTypeMap(Parent.class, ParentVO.class);
        propertyMapper.addMapping(Parent::getId, ParentVO::setKey);
        TypeMap<ParentVO, Parent> propertyMapper2 = this.modelMapper.createTypeMap(ParentVO.class, Parent.class);
        propertyMapper2.addMapping(ParentVO::getKey, Parent::setId);
    }
}
