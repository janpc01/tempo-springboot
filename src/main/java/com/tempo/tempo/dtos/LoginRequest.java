package com.tempo.tempo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    @Email
    private String email; // Optional for SSO

    @NotBlank
    private String password; // Optional for SSO
}