package com.admission.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admission.dto.LoginDto;
import com.admission.dto.LoginResponse;
import com.admission.jwt.JwtUtil;
import com.admission.repository.UserRepo;
import com.admission.service.AuthService;
import com.admission.service.JwtBlacklistService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

         // 🔐 SUCCESS → handled in service
            authService.handleLoginSuccess(request.getUserName());

            String token = jwtUtil.generateToken(request.getUserName());

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (BadCredentialsException ex) {

        	// 🔐 FAILURE → handled in service
            authService.handleLoginFailure(request.getUserName());

            return ResponseEntity
                    .status(401)
                    .body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

    	authService.logout(request.getHeader("Authorization"));

    	return ResponseEntity.ok().build();
//        return ResponseEntity.ok("Logout successful");
    }
    
    @PostMapping("/view")
    public ResponseEntity<?> view() {
     return ResponseEntity.ok("view");
    }
}