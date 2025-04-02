package com.tempo.tempo.controllers;

import com.tempo.tempo.dtos.LoginRequest;
import com.tempo.tempo.dtos.RegisterRequest;
import com.tempo.tempo.entities.User;
import com.tempo.tempo.services.UserService;
import com.tempo.tempo.utils.JwtUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    
    public UserController(UserService userService) {
        this.userService = userService;
        this.jwtUtils = new JwtUtils();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        log.warn("Login request: " + request);
        User user = userService.loginUser(request);

        String accessToken = jwtUtils.generateToken(user.getId());
        log.info(accessToken);
        return ResponseEntity.ok(accessToken);
    }
}
