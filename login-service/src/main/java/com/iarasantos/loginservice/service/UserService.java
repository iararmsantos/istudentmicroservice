package com.iarasantos.loginservice.service;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.data.vo.v1.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();

    UserResponse getUser(Long userId);

    UserResponse createUser(UserRequest request);

    void deleteUser(Long id);

    UserResponse updateUser(UserRequest request);

    UserResponse getByUserId(String userId);
}
