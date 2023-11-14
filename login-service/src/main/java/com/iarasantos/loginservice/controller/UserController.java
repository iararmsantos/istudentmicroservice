package com.iarasantos.loginservice.controller;

import com.iarasantos.loginservice.constants.AppConstants;
import com.iarasantos.loginservice.dto.UserRequest;
import com.iarasantos.loginservice.dto.UserResponse;
import com.iarasantos.loginservice.model.User;
import com.iarasantos.loginservice.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsers() {
        return service.getUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRequest request) {
        service.createUser(request);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String userId) {
        return Optional.ofNullable(userId)
                .map(u -> Long.valueOf(userId))
                .map(service::getUser)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String userId) {
        User user = Optional.ofNullable(userId)
                .map(u -> Long.valueOf(userId))
                .map(service::getUser)
                .orElseThrow();
        service.deleteUser(user.getId());
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable("id") String userId, @RequestBody UserRequest request) {
        validateForPatch(request);
        User user = Optional.ofNullable(userId)
                .map(u -> Long.valueOf(userId))
                .map(service::getUser)
                .orElseThrow();
        service.updateUser(user, request);
    }

    private void validateForPatch(UserRequest request) {
        if (request.getEmail() != null && request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank.");
        }

        if (request.getEmail() != null && !Pattern.matches(AppConstants.EMAIL_REGEXPR, request.getEmail())) {
            throw new IllegalArgumentException("Email is not valid.");
        }

        if (request.getFirstName() != null && request.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First Name cannot be blank.");
        }

        if (request.getLastName() != null && request.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last Name cannot be blank.");
        }
    }
}
