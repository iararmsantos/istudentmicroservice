package com.iarasantos.studentservice.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentParentVO {

    private Long id;

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonProperty("student_id")
    private Long studentId;

}
