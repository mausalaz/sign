package com.bci.sign.response;

import com.bci.sign.entity.Phone;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseLogin implements Serializable {

    private static final long serialVersionUID = 526264658120809018L;

    private String id;
    private String name;
    private String email;
    private String password;
    private String created;
    private String lastLogin;
    private String token;
    private boolean isActive;
    private List<Phone> phones;
}
