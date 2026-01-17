package com.eCommerce.security.jwt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    @NotBlank
    private String username;
    @Email
    private String email;
    private Set<String> roles;
    private String password;

}
