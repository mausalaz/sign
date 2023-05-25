package com.bci.sign.handleError;

import java.io.Serializable;
import java.util.List;

import com.bci.sign.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError implements Serializable {

    private static final long serialVersionUID = 5918799564020496347L;
    private List<ErrorResponse> error;

}
