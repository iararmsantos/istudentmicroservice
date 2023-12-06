package com.iarasantos.studentservice.data.vo.v1;

import com.iarasantos.studentservice.model.StudentParent;
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
    private List<StudentParent> parents;

}
