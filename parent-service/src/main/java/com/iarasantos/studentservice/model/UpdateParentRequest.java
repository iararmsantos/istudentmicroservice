package com.iarasantos.studentservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.studentservice.constants.AppConstant;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParentRequest {
    @JsonProperty("first_name")
    @Column(length = 30)
    private String firstName;
    @JsonProperty("last_name")
    @Column(length = 30)
    private String lastName;
    private String phone;
    @Pattern(regexp = AppConstant.EMAIL_REGEXPR, message = "Email must be valid.")
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
}
