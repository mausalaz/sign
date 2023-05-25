package com.bci.sign.service;

import com.bci.sign.entity.Phone;
import com.bci.sign.entity.UserEntity;
import com.bci.sign.exception.UserException;
import com.bci.sign.jwt.JwtTokenUtil;
import com.bci.sign.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    @InjectMocks
    JwtUserDetailsService jwtUserDetailsService;

    @Mock
    UserRepository userRepository;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeTestClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveUserTest() throws UserException {
        UserEntity userEntityNum = new UserEntity();
        userEntityNum.setUserid("1");
        List<Phone> phones = Arrays.asList(new Phone(1L, 12L, 77, "45", userEntityNum));
        UserEntity userEntity = new UserEntity("1","mau","test@test.com", "pasH34sword",
                new Date(1222233), null,"token", true, phones );

        jwtUserDetailsService.saveUser(userEntity);

        verify(userRepository, times(1)).save(userEntity);
    };
    @Test
    public void saveUserBadPassTest() throws UserException {
        UserEntity userEntityNum = new UserEntity();
        userEntityNum.setUserid("1");
        List<Phone> phones = Arrays.asList(new Phone(1L, 12L, 77, "45", userEntityNum));
        UserEntity userEntity = new UserEntity("1","mau","test@test.com", "password",
                new Date(1222233), null,"token", true, phones );

        UserException  actualException = assertThrows(UserException.class,
                ()-> jwtUserDetailsService.saveUser(userEntity));

        assertTrue(actualException.getMessage().contains("password must have only one uppercase letter"));
        //assertEquals("password must have only one uppercase letter, at least one lowercase letter, only two digits, consecutive or not, and be between 8 and 12 characters long", userException.getMessage());
    };

    @Test
    public void saveUserBadMailTest() throws UserException {
        UserEntity userEntityNum = new UserEntity();
        userEntityNum.setUserid("1");
        List<Phone> phones = Arrays.asList(new Phone(1L, 12L, 77, "45", userEntityNum));
        UserEntity userEntity = new UserEntity("1","mau","testtest.com", "pasH34sword",
                new Date(1222233), null,"token", true, phones );

        UserException  actualException = assertThrows(UserException.class,
                ()-> jwtUserDetailsService.saveUser(userEntity));

        assertTrue(actualException.getMessage().contains("email must have the format"));
    };

    @Test
    public void loadUserByUsernameTest() throws UserException {
        UserEntity userEntityNum = new UserEntity();
        userEntityNum.setUserid("1");
        List<Phone> phones = Arrays.asList(new Phone(1L, 12L, 77, "45", userEntityNum));
        UserEntity userEntity = new UserEntity("1","mau","test@test.com", "pasH34sword",
                new Date(1222233), null,"token", true, phones );

        UserDetails userDetails = new User(userEntity.getEmail(), userEntity.getPassword(),
                new ArrayList<>());
        Mockito.when(userRepository.findUserByEmailNamedParam("test@test.com")).thenReturn(Optional.of(userEntity));
        jwtUserDetailsService.loadUserByUsername("test@test.com");

        verify(userRepository, times(1)).findUserByEmailNamedParam("test@test.com");
    };

    @Test
    public void loadUserByUsernameNotFoundTest() throws UserException {

        UsernameNotFoundException actualException = assertThrows(UsernameNotFoundException.class,
                ()-> jwtUserDetailsService.loadUserByUsername("testtest.com"));

        assertTrue(actualException.getMessage().contains("User not found with mail"));
    };

    @Test
    public void saveUserAlreadyRegisteredTest() throws UserException {
        UserEntity userEntityNum = new UserEntity();
        userEntityNum.setUserid("1");
        List<Phone> phones = Arrays.asList(new Phone(1L, 12L, 77, "45", userEntityNum));
        UserEntity userEntity = new UserEntity("1","mau","test@test.com", "pasH34sword",
                new Date(1222233), null,"token", true, phones );


        when(userRepository.findUserByEmailNamedParam("test@test.com")).thenReturn(Optional.of(userEntity));
        UserException actualException = assertThrows(UserException.class,
                ()-> jwtUserDetailsService.saveUser(userEntity));

        assertTrue(actualException.getMessage().contains("already registered"));
    };

}

