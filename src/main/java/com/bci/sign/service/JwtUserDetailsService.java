package com.bci.sign.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.jwt.JwtTokenUtil;
import com.bci.sign.repository.UserRepository;
import com.bci.sign.response.UserResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    String regexPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userResponse = userRepo.findUserByEmailNamedParam(username);
        if (!userResponse.isPresent()) {
            throw new UsernameNotFoundException("User not found with mail: " + username);
        }
        return new User(userResponse.get().getEmail(), userResponse.get().getPassword(),
                new ArrayList<>());
    }

    public UserResponse saveUser(UserEntity user) throws UserException {
        logger.info("usuario:" + user.getEmail());

        Optional<UserEntity> userOpt = userRepo.findUserByEmailNamedParam(user.getEmail());
        if (userOpt.isPresent()){
            throw new UserException("User " + user.getEmail() + " already registered.");
        }
        final UserDetails userDetails = new User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
        final String token = jwtTokenUtil.generateToken(userDetails);

        //EmailValidation.patternMatches(emailAddress, regexPattern);
        user.setUserid(UUID.randomUUID().toString());
        user.setActive(true);
        user.setCreated(new Date());
        user.setToken(token);
        userRepo.save(user);
        return new UserResponse(user.getUserid(), user.getCreated(), user.getLastLogin(),
                user.getToken(), user.isActive());

    }

}
