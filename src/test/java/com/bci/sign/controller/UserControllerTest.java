package com.bci.sign.controller;

import com.bci.sign.entity.Phone;
import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.response.UserResponse;
import com.bci.sign.service.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    JwtUserDetailsService jwtUserDetailsService;

    @Test
    public void saveUserTest() throws UserException {
        UserEntity userEntityNum = new UserEntity();
        userEntityNum.setUserid("1");
        List<Phone> phones = Arrays.asList(new Phone(1L, 12L, 77, "45", userEntityNum));
        UserEntity userEntity = new UserEntity("1","mau","test@test.com", "password",
                LocalDateTime.now(), null,"token", true, phones );
        UserResponse userResponse = new UserResponse("1",LocalDateTime.now(), null,
                "token", true);

        when(jwtUserDetailsService.saveUser(userEntity)).thenReturn(userResponse);

        ResponseEntity<Object> result = userController.saveUser(userEntity);

        assertThat(result.getStatusCode() ).isEqualTo(HttpStatus.CREATED);
    }
}