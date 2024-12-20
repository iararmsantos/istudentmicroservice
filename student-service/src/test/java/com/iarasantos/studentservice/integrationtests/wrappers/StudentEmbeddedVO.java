package com.iarasantos.studentservice.integrationtests.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.studentservice.data.vo.v1.StudentVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentEmbeddedVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("studentVOList")
    private List<StudentVO> students;
}
