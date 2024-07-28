package com.iarasantos.studentservice.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentParentVO extends RepresentationModel<StudentParentVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long key;

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonProperty("student_id")
    private Long studentId;

}
