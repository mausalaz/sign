package com.bci.sign.controller;

import com.bci.sign.entity.UserEntity;
import com.bci.sign.request.LoginRequest;
import com.bci.sign.jwt.JwtTokenUtil;
import com.bci.sign.response.ResponseLogin;
import com.bci.sign.service.JwtUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    protected final Log logger = LogFactory.getLog(getClass());
    private final String DATE_PATTERN = "MMM d, uuuu HH:mm:ss a";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@RequestBody LoginRequest login) throws Exception {

        authenticate(login.getEmail(), login.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(login.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        UserEntity user = jwtUserDetailsService.getUserByEmail(login.getEmail());
        user.setToken(token);
        user.setLastLogin(LocalDateTime.now());
        jwtUserDetailsService.updateUser(user);
        return new ResponseEntity<>(this.copyToResponseLogin(user), HttpStatus.OK);
    }

    private String formatDate(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return localDateTime.format(formatter);
    }

    private ResponseLogin copyToResponseLogin(UserEntity userEntity){
        ResponseLogin responseLogin = new ResponseLogin();
        responseLogin.setId(userEntity.getUserid());
        responseLogin.setName(userEntity.getName());
        responseLogin.setEmail(userEntity.getEmail());
        responseLogin.setPassword(userEntity.getPassword());
        responseLogin.setCreated(this.formatDate(userEntity.getCreated()));
        responseLogin.setLastLogin(this.formatDate(userEntity.getLastLogin()));
        responseLogin.setToken(userEntity.getToken());
        responseLogin.setActive(userEntity.isActive());
        responseLogin.setPhones(userEntity.getPhones());

        return responseLogin;
    }
    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
