package com.iarasantos.gradeservice.controller;

import com.iarasantos.gradeservice.dto.GradeResponse;
import com.iarasantos.gradeservice.model.Grade;
import com.iarasantos.gradeservice.dto.GradeRequest;
import com.iarasantos.gradeservice.service.GradeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/grades")
public class GradeController {
    @Autowired
    private GradeService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GradeResponse> getGrades() {
        return service.getGrades();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGrade(@Valid @RequestBody GradeRequest grade) {
        service.createGrade(grade);
    }

    @GetMapping("/{id}")
    public Grade getGrade(@PathVariable("id") String gradeId) {
        return Optional.ofNullable(gradeId)
                .map(c -> Long.valueOf(gradeId))
                .map(service::getGradeById)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteGrade(@PathVariable("id") String gradeId) {
        Grade grade = Optional.ofNullable(gradeId)
                .map(c -> Long.valueOf(gradeId))
                .map(service::getGradeById)
                .orElseThrow();
        service.deleteGrade(grade.getId());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGrade(@PathVariable("id") String gradeId, @RequestBody GradeRequest request) {
        Grade grade = Optional.ofNullable(gradeId)
                .map(c -> Long.valueOf(gradeId))
                .map(service::getGradeById)
                .orElseThrow();
        service.updateGrade(grade, request);
    }
}
