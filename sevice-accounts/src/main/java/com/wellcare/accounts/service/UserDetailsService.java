package com.wellcare.accounts.service;

import com.wellcare.accounts.entity.User;
import com.wellcare.accounts.entity.UserDetails;
import com.wellcare.accounts.repository.UserDetailsRepository;
import com.wellcare.accounts.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    public UserDetailsService(UserDetailsRepository userDetailsRepository, UserRepository userRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create user details and link to user
     */
    public UserDetails saveUserDetails(Long userId, UserDetails details) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        details.setUser(user);
        return userDetailsRepository.save(details);
    }

    /**
     * Update user details
     */
    public UserDetails updateUserDetails(Long userId, UserDetails updatedDetails) {
        UserDetails existing = userDetailsRepository.findByUserId(userId);
        if (existing == null) return null;

        existing.setPhoneNumber(updatedDetails.getPhoneNumber());
        existing.setLocation(updatedDetails.getLocation());
        existing.setExperience(updatedDetails.getExperience());
        existing.setDepartment(updatedDetails.getDepartment());
        existing.setJobRole(updatedDetails.getJobRole());
        existing.setAbout(updatedDetails.getAbout());
        existing.setProfileImageUrl(updatedDetails.getProfileImageUrl());

        return userDetailsRepository.save(existing);
    }

    /**
     * Get details by user ID
     */
    public UserDetails getByUserId(Long userId) {
        return userDetailsRepository.findByUserId(userId);
    }
}
