package com.tempo.tempo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    private String username;

    @Email
    private String email; // Optional for SSO

    private String password; // Optional for SSO
}