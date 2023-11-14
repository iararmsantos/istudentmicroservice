package com.iarasantos.loginservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.loginservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String phone;
    private String email;
    private Role role;
}
