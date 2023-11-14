package com.iarasantos.loginservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.loginservice.model.Role;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

@Builder
public class UserResponse {
    @Id
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String phone;

    private String email;

    private Role role;

    @JsonProperty("creation_date")
    private Date creationDate;

    public UserResponse(String firstName, String lastName, String phone, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
}
