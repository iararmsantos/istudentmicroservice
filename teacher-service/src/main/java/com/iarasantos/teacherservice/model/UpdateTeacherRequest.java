package com.iarasantos.teacherservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.teacherservice.constants.AppConstant;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateTeacherRequest {
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
