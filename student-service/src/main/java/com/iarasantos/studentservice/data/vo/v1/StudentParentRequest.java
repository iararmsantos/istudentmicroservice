package com.iarasantos.studentservice.data.vo.v1;

import com.iarasantos.studentservice.model.StudentParent;
import com.iarasantos.studentservice.model.Student;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentParentRequest extends RepresentationModel<StudentParentRequest> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Student student;
    private List<StudentParent> parents;

}
