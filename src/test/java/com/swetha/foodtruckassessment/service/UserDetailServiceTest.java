package com.swetha.foodtruckassessment.service;

import com.swetha.foodtruckassessment.model.User;
import com.swetha.foodtruckassessment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceTest {

    @InjectMocks
    private UserDetailService userDetailService;
    @Mock
    private UserRepository userRepository;
    @Test
    void loadUserByUsername() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        assertDoesNotThrow(() -> userDetailService.loadUserByUsername("email@email.com"));
    }

    @Test
    void loadUserByUsername_notFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailService.loadUserByUsername("email@email.com"),
                "User not found!");
    }
}