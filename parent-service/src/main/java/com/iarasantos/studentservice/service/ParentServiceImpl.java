package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.model.Role;
import com.iarasantos.studentservice.model.Parent;
import com.iarasantos.studentservice.model.UpdateParentRequest;
import com.iarasantos.studentservice.repository.ParentRepository;
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
    public Parent createParent(Parent parent) {
        parent.setRole(Role.PARENT);
        parent.setCreationDate(new Date());
        return repository.save(parent);
    }

    @Override
    public List<Parent> getParents() {
        return (List<Parent>) repository.findAll();
    }

    @Override
    public Parent getParentById(Long parentId) {
        return repository.findParentById(parentId);
    }

    @Override
    public void deleteParent(Long parentId) {
        repository.deleteById(parentId);

    }

    @Override
    public void updateParent(Parent parent, UpdateParentRequest request) {
        setParentUpdate(parent, request);
        repository.save(parent);
    }

    private void setParentUpdate(Parent parent, UpdateParentRequest request) {
        if (request.getFirstName() != null) {
            parent.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            parent.setLastName(request.getLastName());
        }

        if (request.getPhone() != null) {
            parent.setPhone(request.getPhone());
        }

        if (request.getEmail() != null) {
            parent.setEmail(request.getEmail());
        }

        if (request.getRole() != null) {
            parent.setRole(Role.PARENT);
        }
    }
}
