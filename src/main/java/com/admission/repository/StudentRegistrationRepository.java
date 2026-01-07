package com.admission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.entity.StudentRegistration;

public interface StudentRegistrationRepository extends JpaRepository<StudentRegistration, Long> {
	
	Optional<StudentRegistration> findByEmail(String email);

    Optional<StudentRegistration> findByMobileNo(String mobileNo);

}
