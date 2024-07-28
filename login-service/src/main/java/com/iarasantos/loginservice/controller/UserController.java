package com.iarasantos.loginservice.controller;

import com.iarasantos.common.utilcommon.util.MediaType;
import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints to manage api users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all users", description = "Find all users",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(implementation = UserRequest.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = @Content)
            })
    public List<UserRequest> getUsers() {
        return service.getUsers();
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a user",
            description = "Create user by passing in a JSON, XML, or YAML representation of a student",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = UserRequest.class))
                    ),
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = @Content)
            })
    public UserRequest createUser(@Valid @RequestBody UserRequest request) {
        return service.createUser(request);
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @Operation(summary = "Find one user", description = "Find one user",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = UserRequest.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204",
                            content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = @Content)
            })
    public UserRequest getUser(@PathVariable("id") Long userId) {
        return service.getUser(userId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete one user", description = "Delete one user",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204",
                            content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400",
                            content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = @Content)
            })
    public void deleteUser(@PathVariable("id") Long userId) {
        service.deleteUser(userId);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @Operation(summary = "Update one user",
            description = "Update one user by passing in a JSON, XML, or YAML representation of a student",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = UserRequest.class))
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
    public UserRequest updateUser(@RequestBody UserRequest request) {
        return service.updateUser(request);
    }


}
