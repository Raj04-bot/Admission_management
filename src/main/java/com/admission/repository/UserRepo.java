package com.admission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.entity.User;

public interface UserRepo  extends JpaRepository<User, Long>{
	
	 Optional<User> findByEmail(String email);
	 
	 boolean existsByEmail(String email);

	 Optional<User> findByUserName(String username);
	
	

}
