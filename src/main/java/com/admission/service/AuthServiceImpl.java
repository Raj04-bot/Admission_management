package com.admission.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.jwt.JwtUtil;
import com.admission.repository.UserRepo;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
	
	private static final int MAX_FAILED_ATTEMPTS = 5;

    private final UserRepo userRepository;
    private final JwtBlacklistService blacklistService;
    private final JwtUtil jwtUtil;

    // 🔐 Call ONLY after successful authentication
    public void handleLoginSuccess(String email) {

        userRepository.findByEmail(email).ifPresent(user -> {
            user.setWrongLoginCount(0);
            user.setLocked(false);
            user.setLoggedIn(true);
            userRepository.save(user);
        });
    }

    // 🔐 Call ONLY after authentication failure
    public void handleLoginFailure(String email) {

        userRepository.findByEmail(email).ifPresent(user -> {

            int attempts = user.getWrongLoginCount() + 1;
            user.setWrongLoginCount(attempts);

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                user.setLocked(true);
            }

            userRepository.save(user);
        });
    }
    
    public void logout(String authorizationHeader) {
    	
    	if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return;
            }

            String token = authorizationHeader.substring(7);

            try {
                // 🔐 Invalidate JWT
                blacklistService.blacklistToken(token);

                // 🔐 Update user state (optional)
                String email = jwtUtil.extractUserName(token);

                userRepository.findByEmail(email)
			                    .ifPresent(user -> {
			                        user.setLoggedIn(false);
			                        userRepository.save(user);
			                    });

            } catch (JwtException | IllegalArgumentException ex) {
                log.error("Exception Occurred in logout at AuthServiceImpl ->" +ex);
            }
        }


}
