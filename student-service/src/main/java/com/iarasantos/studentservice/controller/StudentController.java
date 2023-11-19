package com.iarasantos.studentservice.controller;

import com.iarasantos.studentservice.constants.AppConstant;
import com.iarasantos.studentservice.dto.StudentParentRequest;
import com.iarasantos.studentservice.dto.StudentRequest;
import com.iarasantos.studentservice.model.ParentList;
import com.iarasantos.studentservice.model.Student;
import com.iarasantos.studentservice.service.StudentService;
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
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping
    public List<Student> getStudents() {
        return service.getStudents();
    }

    @PostMapping
    public void createStudent(@Valid @RequestBody Student student) {
        service.createStudent(student);
    }

    @PostMapping("/parents")
    public List<ParentList> createStudentWithParents(@Valid @RequestBody StudentParentRequest studentParentRequest) {
        return service.createStudentWithParents(studentParentRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student getStudent(@PathVariable("id") String studentId) {
        return Optional.ofNullable(studentId)
                .map(s -> Long.valueOf(studentId))
                .map(service::getStudentById)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("id") String studentId) {
        Student student = Optional.ofNullable(studentId)
                .map(s -> Long.valueOf(studentId))
                .map(service::getStudentById)
                .orElseThrow();
        service.deleteStudent(student.getId());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStudent(@PathVariable("id") String studentId, @RequestBody StudentRequest request) {
        validateForUpdate(request);
        Student student = Optional.ofNullable(studentId)
                .map(s -> Long.valueOf(studentId))
                .map(service::getStudentById)
                .orElseThrow();
        service.updateStudent(student, request);
    }

    private void validateForUpdate(StudentRequest request) {
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
