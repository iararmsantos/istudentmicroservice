package com.iarasantos.loginservice.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import com.iarasantos.loginservice.model.Role;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@JsonPropertyOrder({"key", "first_name", "last_name", "phone", "email", "role", "creation_date"})
public class UserRequest extends RepresentationModel<UserRequest> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("id")
    private Long key;

    @NotNull
    @JsonProperty("first_name")
    @NotEmpty(message = "First name cannot be null or empty")
    private String firstName;

    @NotNull
    @JsonProperty("last_name")
    @NotEmpty(message = "Last name cannot be null or empty")
    private String lastName;

    private String phone;

    @NotNull
    @Email
    private String email;

    private Role role;

    @NotNull
    private String password;

    @JsonProperty("creation_date")
    private Date creationDate;
}
