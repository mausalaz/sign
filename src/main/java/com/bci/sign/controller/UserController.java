package com.bci.sign.controller;

import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.response.UserResponse;
import com.bci.sign.service.JwtUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private static final Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @GetMapping("/test")
    public String test(){
        return "hola mundooo";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> saveUser(@RequestBody UserEntity user) throws UserException {

        UserResponse userResponse = jwtUserDetailsService.saveUser(user);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

}
