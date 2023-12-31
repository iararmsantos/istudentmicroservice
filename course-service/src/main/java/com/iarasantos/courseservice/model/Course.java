package com.iarasantos.courseservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import java.util.Date;
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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    @NotEmpty(message = "Title cannot be null or empty")
    private String title;

    @Enumerated(EnumType.STRING)
    private Season section;

    @Column
    private int year;

    @Column(name = "teacher_id")
    @JsonProperty("teacher_id")
    private Long teacherId;

    @CreationTimestamp
    @JsonProperty("creation_date")
    private Date creationDate;
}
