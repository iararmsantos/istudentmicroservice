package com.iarasantos.gradeservice.controller;

import com.iarasantos.gradeservice.data.vo.v1.GradeResponse;
import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.model.Grade;
import com.iarasantos.gradeservice.data.vo.v1.GradeRequest;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    @Autowired
    private GradeService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GradeVO> getGrades() {
        return service.getGrades();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GradeVO createGrade(@Valid @RequestBody GradeVO grade) {
        return service.createGrade(grade);
    }

    @GetMapping("/{id}")
    public GradeVO getGrade(@PathVariable("id") Long gradeId) {
        return service.getGradeById(gradeId);
    }

    @DeleteMapping("/{id}")
    public void deleteGrade(@PathVariable("id") Long gradeId) {
        service.deleteGrade(gradeId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GradeVO updateGrade(@RequestBody GradeVO request) {
        return service.updateGrade(request);
    }
}
