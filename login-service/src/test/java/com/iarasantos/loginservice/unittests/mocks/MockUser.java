package com.iarasantos.loginservice.unittests.mocks;

import com.iarasantos.loginservice.data.vo.v1.UserVO;
import com.iarasantos.loginservice.model.Role;
import com.iarasantos.loginservice.model.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockUser {

    public User mockEntity() {
        return mockEntity(0);
    }

    public UserVO mockVO() {
        return mockVO(0);
    }

    public List<UserVO> mockVOList() {
        List<UserVO> users = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            users.add(mockVO(i));
        }
        return users;
    }

    public List<User> mockEntityList() {
        List<User> users = new ArrayList<>();
        for(int i = 0; i< 14; i++) {
            users.add(mockEntity(i));
        }
        return users;
    }

    public User mockEntity(Integer number) {
        User user = new User();
        user.setId(Long.valueOf(number));
        user.setFirstName("First Name Test" + number);
        user.setLastName("Last Name Test" + number);
        user.setEmail("Email Test" + number);
        user.setPhone("Phone Test" + number);
        user.setRole(Role.STUDENT);

        user.setCreationDate(new Date());

        return user;
    }

    public UserVO mockVO(Integer number) {
        UserVO user = new UserVO();
        user.setKey(Long.valueOf(number));
        user.setFirstName("First Name Test" + number);
        user.setLastName("Last Name Test" + number);
        user.setEmail("Email Test" + number);
        user.setPhone("Phone Test" + number);
        user.setRole(Role.STUDENT);

        user.setCreationDate(new Date());

        return user;
    }
}
