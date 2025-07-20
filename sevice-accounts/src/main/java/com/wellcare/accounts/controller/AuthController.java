package com.wellcare.accounts.controller;

import com.wellcare.accounts.DTO.*;
import com.wellcare.accounts.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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

}

