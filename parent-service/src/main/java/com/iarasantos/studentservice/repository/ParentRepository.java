package com.iarasantos.studentservice.repository;

import com.iarasantos.studentservice.model.Parent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Long> {
    Parent findParentById(Long parentId);

    void deleteById(Long parentId);
}
