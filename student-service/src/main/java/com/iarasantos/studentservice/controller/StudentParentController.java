package com.iarasantos.studentservice.controller;

import com.iarasantos.common.utilcommon.util.MediaType;
import com.iarasantos.studentservice.data.vo.v1.StudentParentRequest;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import com.iarasantos.studentservice.service.StudentParentsService;
import com.iarasantos.studentservice.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/parents")
@Tag(name = "StudentParents", description = "Endpoints to manage students and its parents")
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
    @Operation(summary = "Create student with parents",
            description = "Create student with parents by passing in a JSON, XML, or YAML representation of a student",
            tags = {"Students"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = StudentParentRequest.class))
                    ),
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = @Content)
            })
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
//        //TODO: get studentParent the  return  will be StudentParentRequest
//    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete one student", description = "Delete one student",
            tags = {"Students"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204",
                            content = @Content(
                                    schema = @Schema(implementation = StudentVO.class))
                    ),
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = @Content)
            })
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
