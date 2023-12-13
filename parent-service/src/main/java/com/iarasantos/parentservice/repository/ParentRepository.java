package com.iarasantos.parentservice.repository;

import com.iarasantos.parentservice.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findParentById(Long parentId);

    void deleteById(Long parentId);
}
