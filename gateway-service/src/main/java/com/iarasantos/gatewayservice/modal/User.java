package com.iarasantos.gatewayservice.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
public class User {
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String phone;
    private String email;
    private Role role;
    @CreationTimestamp
    @JsonProperty("creation_date")
    private Date creationDate;
}
