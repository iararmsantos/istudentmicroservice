package com.iarasantos.studentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iarasantos.studentservice.model.Student;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentListRequest {
    @JsonProperty("parent_id")
    private Long parentId;

    private Student student;


}
