package com.wellcare.accounts.repository;

import com.wellcare.accounts.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
    UserDetails findByUserId(Long userId);
}
