package com.swetha.foodtruckassessment.service;

import com.swetha.foodtruckassessment.model.UserDetail;
import com.swetha.foodtruckassessment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
