package com.iarasantos.loginservice.service;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import java.util.List;

public interface UserService {
    public List<UserRequest> getUsers();

    UserRequest getUser(Long userId);

    public UserRequest createUser(UserRequest request);

    public void deleteUser(Long id);

    public UserRequest updateUser(UserRequest request);
}
