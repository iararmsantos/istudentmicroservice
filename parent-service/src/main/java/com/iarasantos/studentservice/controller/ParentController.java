package com.iarasantos.studentservice.controller;

import com.iarasantos.studentservice.constants.AppConstant;
import com.iarasantos.studentservice.model.Parent;
import com.iarasantos.studentservice.model.UpdateParentRequest;
import com.iarasantos.studentservice.service.ParentService;
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
@RequestMapping("/parents")
public class ParentController {
    @Autowired
    private ParentService service;

    @GetMapping
    public List<Parent> getParents() {
        return service.getParents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createParent(@Valid @RequestBody Parent parentId) {
        service.createParent(parentId);
    }

    @GetMapping("/{id}")
    public Parent getParent(@PathVariable("id") String parentId) {
        return Optional.ofNullable(parentId)
                .map(s -> Long.valueOf(parentId))
                .map(service::getParentById)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteParent(@PathVariable("id") String parentId) {
        Parent parent = Optional.ofNullable(parentId)
                .map(s -> Long.valueOf(parentId))
                .map(service::getParentById)
                .orElseThrow();
        service.deleteParent(parent.getId());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateParent(@PathVariable("id") String parentId, @RequestBody UpdateParentRequest request) {
        validateForUpdate(request);
        Parent parent = Optional.ofNullable(parentId)
                .map(s -> Long.valueOf(parentId))
                .map(service::getParentById)
                .orElseThrow();
        service.updateParent(parent, request);
    }

    private void validateForUpdate(UpdateParentRequest request) {
        if (request.getEmail() != null && request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank.");
        }

        if (request.getEmail() != null && !Pattern.matches(AppConstant.EMAIL_REGEXPR, request.getEmail())) {
            throw new IllegalArgumentException("Email is not valid.");
        }

        if (request.getFirstName() != null && request.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First Name cannot be blank.");
        }

        if (request.getLastName() != null && request.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last Name cannot be blank.");
        }

        if (request.getPhone() != null && request.getPhone().isBlank()) {
            throw new IllegalArgumentException("Phone cannot be blank.");
        }
    }
}
