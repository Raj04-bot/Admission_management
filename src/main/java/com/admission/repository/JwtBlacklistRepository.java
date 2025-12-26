package com.admission.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.entity.JwtBlacklist;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {

	boolean existsByToken(String token);
}
