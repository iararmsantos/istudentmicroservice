package com.iarasantos.gradeservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("letter_grade")
    @Column(length = 1)
    private String letterGrade;

    @JsonProperty("number_grade")
    @Column(name = "number_grade")
    private Double numberGrade;

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("course_id")
    @Column(name = "course_id")
    private Long courseId;

    @CreationTimestamp
    @JsonProperty("creation_date")
    private Date creationDate;
}
