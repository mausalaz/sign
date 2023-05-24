package com.bci.sign.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.bci.sign.Util.UtilValidation;
import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.jwt.JwtTokenUtil;
import com.bci.sign.repository.UserRepository;
import com.bci.sign.response.UserResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    static final String regexPatternEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    static final String regexPatterPassword =  "^(?=.*[A-Z])(?=.*[a-z].*[a-z])(?=.*\\d.*\\d)[A-Za-z\\d]{8,12}$";

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


        if (!UtilValidation.patternMatches(user.getEmail(), regexPatternEmail)) {
            throw new UserException("email must have the format aaaaaaa@undominio.algo");
        }else if (!UtilValidation.patternMatches(user.getPassword(), regexPatterPassword)) {
            throw new UserException("password must have only one uppercase letter, at least one lowercase letter, only two digits, consecutive or not, and be between 8 and 12 characters long");
        }else{
            Optional<UserEntity> userOpt = userRepo.findUserByEmailNamedParam(user.getEmail());
            if (userOpt.isPresent()) {
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
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return new UserResponse(user.getUserid(), user.getCreated(), user.getLastLogin(),
                    user.getToken(), user.isActive());
        }
    }
}
