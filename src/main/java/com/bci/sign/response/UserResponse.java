package com.bci.sign.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private Date created;
    private Date lastLogin;
    private String token;
    private boolean isActive;
}
