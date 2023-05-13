package com.swetha.foodtruckassessment.repository;

import com.swetha.foodtruckassessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);
}
