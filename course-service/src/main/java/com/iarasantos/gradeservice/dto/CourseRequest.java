package com.iarasantos.gradeservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.gradeservice.model.Season;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {
    @Column(length = 30)
    private String title;

    @Enumerated(EnumType.STRING)
    private Season section;

    private int year;

    @JsonProperty("teacher_id")
    private Long teacherId;
}
