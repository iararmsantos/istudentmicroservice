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
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {
    @Autowired
    private ParentRepository repository;

    private ModelMapper modelMapper;

    public ParentServiceImpl() {
        modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Override
    public List<ParentVO> getParents() {
        List<Parent> parents = repository.findAll();
        List<ParentVO> parentsVO = parents.stream().map(
                (parent) -> this.modelMapper.map(parent, ParentVO.class)
        ).collect(Collectors.toList());
        parentsVO
                .stream()
                .forEach(parent -> parent
                        .add(linkTo(methodOn(ParentController.class)
                                .getParent(parent.getKey())).withSelfRel()));
        return parentsVO;

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
