package com.swetha.foodtruckassessment.service;

import com.swetha.foodtruckassessment.exception.UserAlreadyExistsException;
import com.swetha.foodtruckassessment.model.User;
import com.swetha.foodtruckassessment.model.dto.UserNewDto;
import com.swetha.foodtruckassessment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    public User fromDto(UserNewDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public void insert(User user) throws UserAlreadyExistsException {
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
