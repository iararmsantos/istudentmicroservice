package com.iarasantos.gatewayservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.gatewayservice.modal.Role;

public class ParentRequest {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")

    private String lastName;
    private String phone;

    private String email;

    private Role role;
}
