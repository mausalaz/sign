package com.bci.sign.controller;

import com.bci.sign.entity.Phone;
import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.jwt.JwtTokenUtil;
import com.bci.sign.request.LoginRequest;
import com.bci.sign.response.ResponseLogin;
import com.bci.sign.service.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationControllerTest {

    @InjectMocks
    JwtAuthenticationController jwtAuthenticationController;

    @Mock
    JwtUserDetailsService jwtUserDetailsService;

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtUserDetailsService userDetailsService;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @Test
    public void loginTest() throws Exception {
        UserEntity userEntityNum = new UserEntity();
        userEntityNum.setUserid("1");
        List<Phone> phones = Arrays.asList(new Phone(1L, 12L, 77, "45", userEntityNum));
        UserEntity userEntity = new UserEntity("1","mau","test@test.com", "password",
                LocalDateTime.now(), null,"token", true, phones );

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mauricio.salazar@gmail.com");
        loginRequest.setPassword("rssH5kh6j");

        when(jwtUserDetailsService.getUserByEmail(loginRequest.getEmail())).thenReturn(userEntity);
        ResponseEntity<ResponseLogin> result = jwtAuthenticationController.login(loginRequest);
        assertThat(result.getStatusCode() ).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void loginDisabledExceptionTest() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mauricio.salazar@gmail.com");
        loginRequest.setPassword("rssH5kh6j");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()))).thenThrow(new DisabledException("USER_DISABLED"));

        Exception actualException = assertThrows(Exception.class,
                ()-> jwtAuthenticationController.login(loginRequest));

        assertTrue(actualException.getMessage().contains("USER_DISABLED"));
    }

    @Test
    public void loginBadCredentialsExceptionTest() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mauricio.salazar@gmail.com");
        loginRequest.setPassword("rssH5kh6j");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()))).thenThrow(new BadCredentialsException("INVALID_CREDENTIALS"));

        Exception actualException = assertThrows(Exception.class,
                ()-> jwtAuthenticationController.login(loginRequest));

        assertTrue(actualException.getMessage().contains("INVALID_CREDENTIALS"));
    }
}