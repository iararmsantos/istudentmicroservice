package com.iarasantos.loginservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("user_id")
    @Column(length = 255)
    private String userId;

    @JsonProperty("first_name")
    @Column(length = 30)
    private String firstName;

    @JsonProperty("last_name")
    @Column(length = 30)
    private String lastName;

    private String phone;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 30)
    private String password;

    @CreationTimestamp
    @JsonProperty("creation_date")
    private Date creationDate;

}
