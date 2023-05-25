package com.bci.sign.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -3181240853874480241L;
    private LocalDateTime timestamp;
    private int codigo;
    private String detail;
}
