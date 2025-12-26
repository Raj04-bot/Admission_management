package com.admission.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.entity.JwtBlacklist;
import com.admission.jwt.JwtUtil;
import com.admission.repository.JwtBlacklistRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtBlacklistServiceImpl implements JwtBlacklistService {
	
		private final JwtBlacklistRepository repository;
	    private final JwtUtil jwtUtil;


    public void blacklistToken(String token) {

        LocalDateTime expiry = jwtUtil.extractExpiration(token)
                       .toInstant()
                       .atZone(ZoneId.systemDefault())
                       .toLocalDateTime();

        JwtBlacklist blacklist = new JwtBlacklist();
        blacklist.setToken(token);
        blacklist.setExpiryTime(expiry);

        repository.save(blacklist);
    }

    public boolean isTokenBlacklisted(String token) {
        return repository.existsByToken(token);
    }

}
