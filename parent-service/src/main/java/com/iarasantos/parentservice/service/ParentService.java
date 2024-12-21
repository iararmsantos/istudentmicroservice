package com.iarasantos.parentservice.service;

import com.iarasantos.parentservice.data.vo.v1.ParentVO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface ParentService {
    ParentVO createParent(ParentVO parent);

    PagedModel<EntityModel<ParentVO>> getParents(Pageable pageable);

    List<ParentVO> getParents();

    ParentVO getParentById(Long parentId);

    void deleteParent(Long parentId);

    ParentVO updateParent(ParentVO request);
}
