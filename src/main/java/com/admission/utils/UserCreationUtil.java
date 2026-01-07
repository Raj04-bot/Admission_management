package com.admission.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.admission.entity.Role;
import com.admission.entity.User;
import com.admission.exception.BusinessException;
import com.admission.repository.RoleRepository;
import com.admission.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCreationUtil {

    private final UserRepo userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUserWithRole(String userName,String name,String mobile,String email,String rawPassword,String roleCode,Long objectId) {

        Role role = roleRepository.findByRoleCode(roleCode)
                					.orElseThrow(() -> new BusinessException("Role not found: " + roleCode));

        User user = new User();
        user.setUserName(userName);
        user.setName(name);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        user.setObjectId(objectId);

        // Default flags
        user.setAllowMultipleSession(false);
        user.setEnabled(true);
        user.setLocked(false);
        user.setLoggedIn(false);
        user.setWrongLoginCount(0);

        return userRepository.save(user);
    }
}

