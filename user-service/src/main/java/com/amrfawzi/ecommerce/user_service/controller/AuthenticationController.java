package com.amrfawzi.ecommerce.user_service.controller;

import com.amrfawzi.ecommerce.user_service.dto.login.AuthenticationRequest;
import com.amrfawzi.ecommerce.user_service.dto.login.AuthenticationResponse;
import com.amrfawzi.ecommerce.user_service.dto.register.UserRegistrationRequest;
import com.amrfawzi.ecommerce.user_service.dto.register.UserResponse;
import com.amrfawzi.ecommerce.user_service.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        UserResponse userResponse = authenticationService.register(request);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request) {

        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

}
