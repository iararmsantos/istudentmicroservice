package com.iarasantos.gradeservice.controller;

import com.iarasantos.common.utilcommon.util.MediaType;
import com.iarasantos.gradeservice.data.vo.v1.GradeVO;
import com.iarasantos.gradeservice.service.GradeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @ResponseStatus(HttpStatus.OK)
    public List<GradeVO> getGrades() {
        return service.getGrades();
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @ResponseStatus(HttpStatus.CREATED)
    public GradeVO createGrade(@Valid @RequestBody GradeVO grade) {
        return service.createGrade(grade);
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public GradeVO getGrade(@PathVariable("id") Long gradeId) {
        return service.getGradeById(gradeId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrade(@PathVariable("id") Long gradeId) {
        service.deleteGrade(gradeId);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @ResponseStatus(HttpStatus.OK)
    public GradeVO updateGrade(@RequestBody GradeVO request) {
        return service.updateGrade(request);
    }
}
