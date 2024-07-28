package com.iarasantos.studentservice.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.iarasantos.studentservice.model.StudentParent;
import com.iarasantos.studentservice.model.Role;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"key", "first_name", "last_name", "phone", "email", "role", "creation_date"})
public class StudentVO extends RepresentationModel<StudentVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long key;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String phone;

    private String email;

    private Role role;

    @JsonProperty("creation_date")
    private Date creationDate;

    private List<StudentParent> studentParents;

}
