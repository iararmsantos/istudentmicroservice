package com.iarasantos.loginservice.service;

import com.iarasantos.loginservice.dto.UserRequest;
import com.iarasantos.loginservice.dto.UserResponse;
import com.iarasantos.loginservice.model.User;
import java.util.List;

public interface UserService {
    public List<UserResponse> getUsers();

    User getUser(Long userId);

    public void createUser(UserRequest request);

    public void deleteUser(Long id);

    public void updateUser(User user, UserRequest request);
}
