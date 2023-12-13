package com.iarasantos.parentservice.exceptions;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date timestamp;
    private String message;
    private String details;


}
