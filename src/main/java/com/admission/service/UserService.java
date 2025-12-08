package com.admission.service;

import org.springframework.stereotype.Service;

import com.admission.entity.User;

@Service
public interface UserService {
	
	User saveUser(User user);
	User findByEmail(String email);
	boolean existsByEmail(String email);
	
}
