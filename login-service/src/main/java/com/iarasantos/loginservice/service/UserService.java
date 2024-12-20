package com.iarasantos.loginservice.service;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.data.vo.v1.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    PagedModel<EntityModel<UserResponse>> getUsers(Pageable pageable);

    UserResponse getUser(Long userId);

    UserResponse createUser(UserRequest request);

    void deleteUser(Long id);

    UserResponse updateUser(UserRequest request);

    UserResponse getByUserId(String userId);

    UserResponse getUserByEmail(String email);
}
