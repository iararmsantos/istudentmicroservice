package com.iarasantos.gradeservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.gradeservice.model.Season;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    @Id
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Season section;

    private int year;

    @JsonProperty("creation_date")
    private Date creationDate;
}
