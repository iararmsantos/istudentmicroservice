package com.iarasantos.courseservice.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.iarasantos.courseservice.model.Season;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"key", "title", "section", "year", "teacher_id", "creation_date"})
public class CourseVO extends RepresentationModel<CourseVO> implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long key;

    private String title;

    private Season section;

    private int year;

    @JsonProperty("teacher_id")
    private Long teacherId;

    @JsonProperty("creation_date")
    private Date creationDate;
}
