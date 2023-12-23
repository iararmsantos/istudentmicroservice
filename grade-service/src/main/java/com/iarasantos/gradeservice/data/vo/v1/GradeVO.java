package com.iarasantos.gradeservice.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"key", "letter_grade", "number_grade", "student_id", "course_id", "creation_date"})
public class GradeVO extends RepresentationModel<GradeVO> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("id")
    private Long key;

    @JsonProperty("letter_grade")
    private String letterGrade;

    @JsonProperty("number_grade")
    private Double numberGrade;

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("creation_date")
    private Date creationDate;
}
