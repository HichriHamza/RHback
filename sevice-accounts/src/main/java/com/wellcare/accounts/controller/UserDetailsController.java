package com.wellcare.accounts.controller;


import com.wellcare.accounts.entity.User;
import com.wellcare.accounts.entity.UserDetails;
import com.wellcare.accounts.repository.UserRepository;
import com.wellcare.accounts.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
public class UserDetailsController {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    /**
     * Create user details for the logged-in user
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PostMapping
    public ResponseEntity<UserDetails> createUserDetails(@RequestBody UserDetails details,
                                                         Authentication authentication) {
        // Get user ID using email
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Call the correct service method
        return ResponseEntity.ok(userDetailsService.saveUserDetails(user.getId(), details));
    }

    /**
     * Update user details (only owner or admin)
     */
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDetails> updateUserDetails(
            @PathVariable Long userId,
            @RequestBody UserDetails details) {

        UserDetails updated = userDetailsService.updateUserDetails(userId, details);
        return ResponseEntity.ok(updated);
    }

    /**
     * Get details by user ID (self or admin)
     */
    @PreAuthorize("authentication.name == @userRepository.findById(#userId).get().email")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable Long userId) {
        return ResponseEntity.ok(userDetailsService.getByUserId(userId));
    }

    /**
     * Get details of the currently authenticated user (no userId required)
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/me")
    public UserDetails getMyDetails(Authentication authentication) {
        String email= authentication.getName();
        System.out.println(email);
        User user= userRepository.findByEmail(email).orElseThrow(()->new RuntimeException(email));
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(user.getId());

        return userDetailsService.getByUserId(user.getId());
    }
}
