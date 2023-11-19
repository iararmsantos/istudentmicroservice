package com.iarasantos.gradeservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeRequest {
    @JsonProperty("letter_grade")
    @Column(length = 1)
    private String letterGrade;

    @JsonProperty("number_grade")
    private Double numberGrade;

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("course_id")
    private Long courseId;
}
