package com.wellcare.accounts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("TestController")
@RequiredArgsConstructor
public class TestController {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/dashboard")
    public ResponseEntity<String> getAdminDashboard() {
        return ResponseEntity.ok("Welcome Admin");
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/test/invalid")
    public ResponseEntity<String> get403forbidden(){
        return ResponseEntity.ok("invalid user");
    }
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/user/profile")
    public ResponseEntity<String> userProfile() {
        return ResponseEntity.ok("Welcome user");
    }
}
