package com.iarasantos.gatewayservice.modal;

import lombok.Data;

@Data
public class Course {
    private Long id;
    private String title;
    private Section section;
}
