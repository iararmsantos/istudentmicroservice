package com.iarasantos.gatewayservice.controller;

import com.iarasantos.gatewayservice.dto.StudentRequest;
import com.iarasantos.gatewayservice.modal.Student;
import com.iarasantos.gatewayservice.proxy.StudentProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class GatewayStudentController {
    @Autowired
    private StudentProxy studentProxy;

    @GetMapping
    public Student[] getStudents() {
        return studentProxy.getStudents();
    }

    @PostMapping
    public void create(@RequestBody Student student) {
        studentProxy.createStudent(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")String id) {
        studentProxy.remove(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") String studentId, @RequestBody StudentRequest request) {
        studentProxy.update(studentId, new HttpEntity<StudentRequest>(request));
    }
}
