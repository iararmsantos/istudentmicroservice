package com.iarasantos.parentservice.service;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.parentservice.data.vo.v1.ParentVO;
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
        List<Parent> parents = repository.findAll();
        return DozerMapper.parseListObjects(parents, ParentVO.class);

    }

    @Override
    public ParentVO getParentById(Long parentId) {
        Parent parent = repository.findById(parentId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + parentId + " not found!"));
        return DozerMapper.parseObject(parent, ParentVO.class);
    }

    @Override
    public ParentVO createParent(ParentVO request) {
        Parent parent = Parent.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        parent.setCreationDate(new Date());
        return DozerMapper.parseObject(repository.save(parent), ParentVO.class);
    }

    @Override
    public void deleteParent(Long parentId) {
        repository.deleteById(parentId);
    }

    @Override
    public ParentVO updateParent(ParentVO request) {
        Parent Parent = repository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + request.getId() + " not found!"));
        Parent.setFirstName(request.getFirstName());
        Parent.setLastName(request.getLastName());
        Parent.setPhone(request.getPhone());
        Parent.setEmail(request.getEmail());
        Parent.setRole(request.getRole());

        return DozerMapper.parseObject(repository.save(Parent), ParentVO.class);
    }
}
