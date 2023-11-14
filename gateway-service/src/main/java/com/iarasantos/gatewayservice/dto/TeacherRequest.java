package com.iarasantos.gatewayservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.gatewayservice.modal.Role;
import lombok.Data;

@Data
public class TeacherRequest {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")

    private String lastName;
    private String phone;

    private String email;

    private Role role;
}
