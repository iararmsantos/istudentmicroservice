package com.iarasantos.courseservice.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.courseservice.model.Season;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO {

    private Long id;

    private String title;

    private Season section;

    private int year;

    @JsonProperty("teacher_id")
    private Long teacherId;

    @JsonProperty("creation_date")
    private Date creationDate;
}
