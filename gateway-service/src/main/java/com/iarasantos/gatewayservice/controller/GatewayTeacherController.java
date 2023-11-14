package com.iarasantos.gatewayservice.controller;

import com.iarasantos.gatewayservice.dto.TeacherRequest;
import com.iarasantos.gatewayservice.modal.Teacher;
import com.iarasantos.gatewayservice.proxy.TeacherProxy;
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
@RequestMapping("/teachers")
public class GatewayTeacherController {
    @Autowired
    private TeacherProxy teacherProxy;

    @GetMapping
    public Teacher[] getTeacher() {
        return teacherProxy.getTeachers();
    }

    @PostMapping
    public void create(@RequestBody Teacher teacher) {
        teacherProxy.createTeacher(teacher);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        teacherProxy.remove(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") String teacherId, @RequestBody TeacherRequest request) {
        teacherProxy.update(teacherId, new HttpEntity<TeacherRequest>(request));
    }
}
