package com.iarasantos.parentservice.mapper.mocks;

import com.iarasantos.parentservice.data.vo.v1.ParentVO;
import com.iarasantos.parentservice.model.Parent;
import com.iarasantos.parentservice.model.Role;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockParent {

    public Parent mockEntity() {
        return mockEntity(0);
    }

    public ParentVO mockVO() {
        return mockVO(0);
    }

    public List<ParentVO> mockVOList() {
        List<ParentVO> parents = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            parents.add(mockVO(i));
        }
        return parents;
    }

    public List<Parent> mockEntityList() {
        List<Parent> parents = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            parents.add(mockEntity(i));
        }
        return parents;
    }

    public Parent mockEntity(Integer number) {
        Parent parent = new Parent();
        parent.setId(Long.valueOf(number));
        parent.setFirstName("First Name Test" + number);
        parent.setLastName("Last Name Test" + number);
        parent.setEmail("Email Test" + number);
        parent.setPhone("Phone Test" + number);
        parent.setRole(Role.PARENT);

        parent.setCreationDate(new Date());

        return parent;
    }

    public ParentVO mockVO(Integer number) {
        ParentVO parent = new ParentVO();
        parent.setId(Long.valueOf(number));
        parent.setFirstName("First Name Test" + number);
        parent.setLastName("Last Name Test" + number);
        parent.setEmail("Email Test" + number);
        parent.setPhone("Phone Test" + number);
        parent.setRole(Role.PARENT);

        parent.setCreationDate(new Date());

        return parent;
    }
}
