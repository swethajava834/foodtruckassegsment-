package com.swetha.foodtruckassessment.service;

import com.swetha.foodtruckassessment.exception.UserAlreadyExistsException;
import com.swetha.foodtruckassessment.model.User;
import com.swetha.foodtruckassessment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Test
    void insert() throws UserAlreadyExistsException {
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> userService
                .insert(new User(null,"name","email@email.com","password")));
    }

    @Test
    void insert_userAlreadyExists() throws UserAlreadyExistsException {
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        assertThrows(UserAlreadyExistsException.class,
                () -> userService.insert(new User(null,"name","email@email.com","password")),
                "User already exists!");
    }

}