package com.iarasantos.loginservice.unittests.mocks;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.data.vo.v1.UserResponse;
import com.iarasantos.loginservice.model.Role;
import com.iarasantos.loginservice.model.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockUser {

    public UserEntity mockEntity() {
        return mockEntity(0);
    }

    public UserRequest mockVO() {
        return mockVO(0);
    }

    public UserResponse mockResponse() {
        return mockResponse(0);
    }

    public List<UserRequest> mockVOList() {
        List<UserRequest> users = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            users.add(mockVO(i));
        }
        return users;
    }

    public List<UserEntity> mockEntityList() {
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            users.add(mockEntity(i));
        }
        return users;
    }

    public List<UserResponse> mockResponseList() {
        List<UserResponse> users = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            users.add(mockResponse(i));
        }
        return users;
    }

    public UserEntity mockEntity(Integer number) {
        UserEntity user = new UserEntity();
        user.setId(Long.valueOf(number));
        user.setUserId("TestUserId" + number);
        user.setFirstName("First Name Test" + number);
        user.setLastName("Last Name Test" + number);
        user.setEmail("Email Test" + number);
        user.setPhone("Phone Test" + number);
        user.setPassword("password" + number);
        user.setRole(Role.STUDENT);
        user.setCreationDate(new Date());
        return user;
    }

    public UserRequest mockVO(Integer number) {
        UserRequest user = new UserRequest();
        user.setKey(Long.valueOf(number));
        user.setFirstName("First Name Test" + number);
        user.setLastName("Last Name Test" + number);
        user.setEmail("Email Test" + number);
        user.setPhone("Phone Test" + number);
        user.setPassword("password" + number);
        user.setRole(Role.STUDENT);

        user.setCreationDate(new Date());

        return user;
    }

    public UserResponse mockResponse(Integer number) {
        UserResponse user = new UserResponse();
        user.setUserId("TestUserId" + number);
        user.setFirstName("First Name Test" + number);
        user.setLastName("Last Name Test" + number);
        user.setEmail("Email Test" + number);
        user.setPhone("Phone Test" + number);
        user.setRole(Role.STUDENT);

        user.setCreationDate(new Date());

        return user;
    }
}
