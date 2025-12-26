package com.admission.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.admission.entity.User;
import com.admission.security.CustomUserDetails;
import com.admission.security.CustomUserDetailsService;
import com.admission.service.JwtBlacklistService;
import com.admission.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;
    private final JwtBlacklistService blacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Skip JWT checking for public endpoints
        String path = request.getServletPath();
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Read Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // Nothing to validate — continue the chain
        }

        jwt = authHeader.substring(7);
     // 🔴 LOGOUT EFFECT HERE
        if (blacklistService.isTokenBlacklisted(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        userName = jwtUtil.extractUserName(jwt);

        // 3. Validate token
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        	CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        	
        }

        filterChain.doFilter(request, response);
    }
}

