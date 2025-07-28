package com.wellcare.accounts.controller;

import com.wellcare.accounts.DTO.*;
import com.wellcare.accounts.entity.User;
import com.wellcare.accounts.repository.UserRepository;
import com.wellcare.accounts.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    @PostMapping("/register")
        public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
            String token= authService.register(request);
            return ResponseEntity.ok(new AuthResponse(token));
        }
    @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
            String token= authService.authenticate(request);
            return ResponseEntity.ok(new AuthResponse(token));
        }
    @GetMapping("/me")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Extracted from JWT subject

        // Fetch full user info from DB
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found!"));
    }
}

