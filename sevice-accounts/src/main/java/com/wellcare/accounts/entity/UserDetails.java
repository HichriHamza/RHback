package com.wellcare.accounts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;
    private String location;
    private String experience;      // e.g. "3 years"
    private String department;      // e.g. "Engineering"
    private String jobRole;         // e.g. "Frontend Developer"

    @Column(length = 1000)
    private String about;           // description or bio

    private String profileImageUrl; // store image URL or path

    // One-to-One relationship with User
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

}
