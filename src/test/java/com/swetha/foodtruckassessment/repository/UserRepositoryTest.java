package com.swetha.foodtruckassessment.repository;

import com.swetha.foodtruckassessment.model.User;
import com.swetha.foodtruckassessment.FoodTruckAssessmentApplication;
import com.swetha.foodtruckassessment.container.ContainersEnvironment;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FoodTruckAssessmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRepositoryTest extends ContainersEnvironment {

    @Autowired
    private UserRepository userRepository;
    @Test
    void findByEmail() {
        User expectedUser = userRepository.save(User.builder()
                .name("test")
                .email("email@email.com")
                .password("password")
                .build());
        Optional<User> returnedUser = userRepository.findByEmail(expectedUser.getEmail());
        assertTrue(returnedUser.isPresent());
        assertThat(returnedUser.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);
    }

    @Test
    void findByEmail_nonExistingEmail() {
        User expectedUser = User.builder()
                .name("test")
                .email("notregisterel@email.com")
                .password("password")
                .build();
        Optional<User> returnedUser = userRepository.findByEmail(expectedUser.getEmail());
        assertTrue(returnedUser.isEmpty());
    }
}