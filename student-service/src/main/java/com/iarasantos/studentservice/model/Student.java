package com.iarasantos.studentservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.iarasantos.common.utilcommon.constants.AppConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("first_name")
    @Column(length = 30, nullable = false)
    @NotEmpty(message = "First name cannot be null or empty")
    private String firstName;

    @JsonProperty("last_name")
    @Column(length = 30, nullable = false)
    @NotEmpty(message = "Last name cannot be null or empty")
    private String lastName;

    private String phone;

    @Pattern(regexp = AppConstant.EMAIL_REGEXPR, message = "Email must be valid.")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @JsonProperty("creation_date")
    private Date creationDate;

    @Transient
    private List<StudentParent> studentParents;

}
