package com.iarasantos.teacherservice.controller;

import com.iarasantos.teacherservice.constants.AppConstant;
import com.iarasantos.teacherservice.model.Teacher;
import com.iarasantos.teacherservice.model.UpdateTeacherRequest;
import com.iarasantos.teacherservice.service.TeacherService;
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
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService service;

    @GetMapping
    public List<Teacher> getTeachers() {
        return service.getTeachers();
    }

    @PostMapping
    public void createTeacher(@Valid @RequestBody Teacher teacher) {
        service.create(teacher);
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable("id") String teacherId) {
        return Optional.ofNullable(teacherId)
                .map(t -> Long.valueOf(teacherId))
                .map(service::getTeacherById)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable("id") String teacherId) {
        Teacher teacher = Optional.ofNullable(teacherId)
                .map(t -> Long.valueOf(teacherId))
                .map(service::getTeacherById)
                .orElseThrow();
        service.deleteTeacher(teacher.getId());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTeacher(@PathVariable("id")String teacherId, @RequestBody UpdateTeacherRequest request) {
        validateForUpdate(request);
        Teacher teacher = Optional.ofNullable(teacherId)
                .map(t -> Long.valueOf(teacherId))
                .map(service::getTeacherById)
                .orElseThrow();
        service.updateTeacher(teacher, request);
    }

    private void validateForUpdate(UpdateTeacherRequest request) {
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
