package com.amrfawzi.ecommerce.user_service.dto.register;

import com.amrfawzi.ecommerce.user_service.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Set<Role> roles;
}
