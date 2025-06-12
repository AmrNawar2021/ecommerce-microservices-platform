package com.amrfawzi.ecommerce.user_service;

import com.amrfawzi.ecommerce.user_service.dto.login.AuthenticationRequest;
import com.amrfawzi.ecommerce.user_service.dto.login.AuthenticationResponse;
import com.amrfawzi.ecommerce.user_service.dto.register.UserRegistrationRequest;
import com.amrfawzi.ecommerce.user_service.dto.register.UserResponse;
import com.amrfawzi.ecommerce.user_service.model.Role;
import com.amrfawzi.ecommerce.user_service.model.User;
import com.amrfawzi.ecommerce.user_service.repository.UserRepository;
import com.amrfawzi.ecommerce.user_service.security.JwtService;
import com.amrfawzi.ecommerce.user_service.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("test@example.com");
        request.setFullName("Test User");
        request.setPassword("password");
        request.setPhoneNumber("123456789");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User savedUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .fullName("Test User")
                .phoneNumber("123456789")
                .password("encodedPassword")
                .createdAt(LocalDateTime.now())
                .roles(Collections.singleton(Role.ROLE_USER))
                .enabled(true)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");

        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .fullName("Test User")
                .phoneNumber("123456789")
                .roles(Collections.singleton(Role.ROLE_USER))
                .enabled(true)
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("mocked-jwt-token");

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}

