package com.task.orders.service;

import com.task.orders.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomStudentService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userData = userRepo.findByEmail(email);
        if (userData == null) {
            throw new UsernameNotFoundException("User not found ");
        } else {
            String encodedPassword = passwordEncoder.encode(userData.getId().toString());
            return User
                    .withUsername(userData.getEmail())
                    .password(encodedPassword)
                    .roles("USER")
                    .build();
        }
    }

}
