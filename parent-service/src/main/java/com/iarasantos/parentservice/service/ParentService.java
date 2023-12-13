package com.iarasantos.parentservice.service;

import com.iarasantos.parentservice.data.vo.v1.ParentVO;
import java.util.List;

public interface ParentService {
    public ParentVO createParent(ParentVO parent);

    public List<ParentVO> getParents();
    public ParentVO getParentById(Long parentId);
    public void deleteParent(Long parentId);
    public ParentVO updateParent(ParentVO request);
}
