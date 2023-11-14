package com.iarasantos.studentservice.dto;

import com.iarasantos.studentservice.model.ParentList;
import com.iarasantos.studentservice.model.Student;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentParentRequest {
    private Student student;
    private List<ParentList> parents;

}
