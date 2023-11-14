package com.iarasantos.loginservice.service;

import com.iarasantos.loginservice.dto.UserRequest;
import com.iarasantos.loginservice.dto.UserResponse;
import com.iarasantos.loginservice.model.User;
import com.iarasantos.loginservice.repository.UserRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;
    @Override
    public List<UserResponse> getUsers() {
        List<User> users = repository.findAll();
        return users.stream().map(user -> mapToUserResponse(user)).toList();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .creationDate(user.getCreationDate())
                .build();
    }

    @Override
    public User getUser(Long userId) {
        return repository.findUserById(userId);
    }

    @Override
    public void createUser(UserRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        user.setCreationDate(new Date());
        repository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteUserById(userId);
    }

    @Override
    public void updateUser(User user, UserRequest request) {
        setUpdate(user, request);
        repository.save(user);
    }

    public void setUpdate(User user, UserRequest request) {
        if(request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if(request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if(request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if(request.getRole() != null) {
            user.setRole(request.getRole());
        }
    }
}
