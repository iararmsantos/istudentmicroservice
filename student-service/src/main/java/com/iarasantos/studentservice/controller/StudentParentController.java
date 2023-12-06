package com.iarasantos.studentservice.controller;

import com.iarasantos.studentservice.data.vo.v1.StudentParentRequest;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.service.StudentParentsService;
import com.iarasantos.studentservice.service.StudentService;
import com.iarasantos.studentservice.util.MediaType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/parents")
public class StudentParentController {
    @Autowired
    private StudentService service;

    private StudentParentsService stpService;

//    @GetMapping(
//            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
//                    MediaType.APPLICATION_YML})
//    public List<StudentVO> getStudents() {
//        //TODO: get studentparents then the return will be StudentParentRequest
//        return service.getStudents();
//    }

    @PostMapping(value = "/parents",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public StudentParentRequest createStudentWithParents(
            @Valid @RequestBody StudentParentRequest studentParentRequest) {
        return service.createStudentWithParents(studentParentRequest);
    }

//    @GetMapping(value = "/{id}",
//            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
//            MediaType.APPLICATION_YML})
//    @ResponseStatus(HttpStatus.OK)
//    public StudentVO getStudent(@PathVariable("id") Long studentId) {
//        return service.getStudentById(studentId);
//        //TODO: get studentParent the  return will StudentParentRequest
//    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentParents(@PathVariable("id") Long studentId) {
        stpService.deleteStudentParents(studentId);
    }

//    @PutMapping(
//            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
//                    MediaType.APPLICATION_YML},
//            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
//                    MediaType.APPLICATION_YML})
//    @ResponseStatus(HttpStatus.OK)
//    public StudentVO updateStudent(@RequestBody StudentVO request) {
//        return service.updateStudent(request);
//        //update parentId???
//        // receive and return StudentParentRequest
//    }
}
