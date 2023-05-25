package com.bci.sign.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = -8139127205547044330L;

    private String email;
    private String password;


}
