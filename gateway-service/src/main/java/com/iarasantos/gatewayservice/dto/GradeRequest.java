package com.iarasantos.gatewayservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class GradeRequest {
    @JsonProperty("letter_grade")
    @Column(length = 1)
    private String letterGrade;

    @JsonProperty("number_grade")
    private Double numberGrade;
}
