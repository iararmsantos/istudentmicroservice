package com.iarasantos.gatewayservice.controller;


import com.iarasantos.gatewayservice.modal.Grade;
import com.iarasantos.gatewayservice.dto.GradeRequest;
import com.iarasantos.gatewayservice.proxy.GradeProxy;
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
@RequestMapping("/grades")
public class GatewayGradeController {
    @Autowired
    private GradeProxy gradeProxy;

    @GetMapping
    public Grade[] getGrades() {
        return gradeProxy.getGrades();
    }

    @PostMapping
    public void create(@RequestBody Grade grade) {
        gradeProxy.createGrade(grade);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") String id) {
        gradeProxy.remove(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") String gradeId, @RequestBody GradeRequest request) {
        gradeProxy.update(gradeId, new HttpEntity<GradeRequest>(request).getBody());
    }
}
