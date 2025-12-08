package com.admission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admission.entity.User;
import com.admission.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		user.setPassword(user.getPassword());
		return userRepo.save(user);
	}

	@Override
	public User findByEmail(String email) {
	
		return userRepo.findByEmail(email).orElse(null);
	}

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.existsByEmail(email);
	}

}
