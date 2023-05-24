package com.bci.sign.controller;

import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.response.UserResponse;
import com.bci.sign.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping(path= "/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> saveUser(@RequestBody UserEntity user) throws UserException {

        UserResponse userResponse = jwtUserDetailsService.saveUser(user);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

}
