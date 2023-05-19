package com.bci.sign.service;

import com.bci.sign.controller.UserController;
import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepo;
    String regexPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    public UserEntity saveUser(UserEntity user) throws UserException {
        log.info("usuario:" + user.getEmail());
        UserEntity userResponse = userRepo.findUserByEmailNamedParam(user.getEmail());
        if (userResponse != null)
            throw new UserException("The user is already registered");


        //EmailValidation.patternMatches(emailAddress, regexPattern);
        user.setUserid(UUID.randomUUID().toString());
        user.setActive(true);
        user.setCreated(new Date());
        return userRepo.save(user);
    }
}
