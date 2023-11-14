package com.iarasantos.gatewayservice.dto;

import com.iarasantos.gatewayservice.modal.Section;
import lombok.Data;

@Data
public class CourseRequest {
    private String title;
    private Section section;
}
