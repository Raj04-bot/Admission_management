package com.admission.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admission.dto.LoginDto;
import com.admission.entity.User;
import com.admission.jwt.JwtService;
import com.admission.service.UserService;
import com.admission.utils.SecurityHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto request) {
        User user = userService.validateUser(request.getEmail(), request.getPassword());
        User currentUser = SecurityHelper.getCurrentUser();
        return jwtService.generateToken(user);
    }
}