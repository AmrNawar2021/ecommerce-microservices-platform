package com.amrfawzi.ecommerce.user_service;

import com.amrfawzi.ecommerce.user_service.model.Role;
import com.amrfawzi.ecommerce.user_service.model.User;
import com.amrfawzi.ecommerce.user_service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // هنا بنحقن قيم الـ secret والـ expiration يدويا بدل ما ناخدهم من properties
        ReflectionTestUtils.setField(jwtService, "secretKey", "MySuperSecretKeyMySuperSecretKey12345678MySuperSecretKeyMySuperSecretKey12345678MySuperSecretKeyMySuperSecretKey12345678");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1000 * 60 * 60); // ساعة
    }

    @Test
    void testGenerateAndValidateToken() {
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = new User();
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setEmail("test@example.com");
        user.setEnabled(true);
        user.setFullName("Amr Fawzi");
        user.setPassword("123");
        user.setCreatedAt(localDateTime);
        String token = jwtService.generateToken(user);
        assertNotNull(token);

        String username = jwtService.extractUsername(token);
        assertEquals("test@example.com", username);

        boolean valid = jwtService.isTokenValid(token, "test@example.com");
        assertTrue(valid);
    }
}

