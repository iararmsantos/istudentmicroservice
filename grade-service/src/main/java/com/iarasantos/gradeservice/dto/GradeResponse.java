package com.iarasantos.gradeservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponse {
    @Id
    private Long id;

    @JsonProperty("letter_grade")
    private String letterGrade;

    @JsonProperty("number_grade")
    private Double numberGrade;

    @JsonProperty("creation_date")
    private Date creationDate;
}
