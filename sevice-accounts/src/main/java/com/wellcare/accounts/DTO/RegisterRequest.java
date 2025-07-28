package com.wellcare.accounts.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String lastname;
    private String password;
    private String email;
    private String department;
    private Boolean isAdmin;
    private Boolean isEmployee;

}
