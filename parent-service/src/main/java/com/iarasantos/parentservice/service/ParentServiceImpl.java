package com.iarasantos.parentservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.parentservice.controller.ParentController;
import com.iarasantos.parentservice.data.vo.v1.ParentVO;
import com.iarasantos.parentservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.parentservice.exceptions.ResourceNotFoundException;
import com.iarasantos.parentservice.model.Parent;
import com.iarasantos.parentservice.repository.ParentRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {
    @Autowired
    private ParentRepository repository;

    @Override
    public List<ParentVO> getParents() {
        List<ParentVO> parents = DozerMapper
                .parseListObjects(repository.findAll(), ParentVO.class);
        parents
                .stream()
                .forEach(parent -> parent
                        .add(linkTo(methodOn(ParentController.class)
                                .getParent(parent.getKey())).withSelfRel()));
        return parents;

    }

    @Override
    public ParentVO getParentById(Long parentId) {
        Parent parent = repository.findById(parentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + parentId + " not found!"));
        ParentVO vo = DozerMapper.parseObject(parent, ParentVO.class);

        vo.add(linkTo(methodOn(ParentController.class).getParent(vo.getKey())).withSelfRel());

        return vo;
    }

    @Override
    public ParentVO createParent(ParentVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }

        Parent parent = DozerMapper.parseObject(request, Parent.class);
        parent.setCreationDate(new Date());
        ParentVO vo = DozerMapper.parseObject(repository.save(parent), ParentVO.class);

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

        ParentVO vo = DozerMapper.parseObject(repository.save(parent), ParentVO.class);

        vo.add(linkTo(methodOn(ParentController.class).getParent(vo.getKey())).withSelfRel());

        return vo;
    }
}
