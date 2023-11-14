package com.iarasantos.studentservice.service;

import com.iarasantos.studentservice.model.Parent;
import com.iarasantos.studentservice.model.UpdateParentRequest;
import java.util.List;

public interface ParentService {
    public Parent createParent(Parent parent);

    public List<Parent> getParents();
    public Parent getParentById(Long parentId);
    public void deleteParent(Long parentId);
    public void updateParent(Parent parent, UpdateParentRequest request);
}
