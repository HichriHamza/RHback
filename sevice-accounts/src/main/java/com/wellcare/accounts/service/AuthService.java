package com.wellcare.accounts.service;


import com.wellcare.accounts.DTO.AuthRequest;
import com.wellcare.accounts.DTO.RegisterRequest;
import com.wellcare.accounts.config.JwtUtil;
import com.wellcare.accounts.entity.User;
import com.wellcare.accounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username Already Exists! ");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already Exists !!");
        }
        Set<String> roles = new HashSet<>();
        if (request.getIsAdmin() ) roles.add("ROLE_ADMIN");
        if (request.getIsEmployee()) roles.add("ROLE_EMPLOYEE");

        User user= User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .department(request.getDepartment())
                .isAdmin(request.getIsAdmin())
                .isEmployee(request.getIsEmployee())
                .roles(roles)
                .build();
        userRepository.save(user);

        return jwtUtil.generateToken(user.getUsername(), user.getRoles());

    }
    public String authenticate(AuthRequest request){
        User user= userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new RuntimeException("User Not Found !! "));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials ! ");

        }
        return jwtUtil.generateToken(user.getUsername(), user.getRoles());

    }
}
