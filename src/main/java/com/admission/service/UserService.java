package com.admission.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.admission.entity.User;
import com.admission.repository.UserRepo;
import com.admission.utils.CustomUserDetails;

@Service
public class UserService implements UserDetailsService {

	@Autowired
    private UserRepo userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }

    // Optional: validate login credentials manually
    public User validateUser(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Compare passwords (add password encoder in production)
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }
}

