package com.bci.sign.controller;

import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.service.UserService;
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
    private UserService userService;
    @GetMapping("/test")
    public String test(){
        return "hola mundooo";
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user){
        UserEntity userResponse = null;
        try {
            userResponse = userService.saveUser(user);
        }catch (Exception ex){
            log.error("error is: {}", ex);
            return new ResponseEntity(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<UserEntity>(userResponse, HttpStatus.CREATED);
    }
}
