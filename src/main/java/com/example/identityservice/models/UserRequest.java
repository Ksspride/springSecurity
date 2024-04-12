package com.example.identityservice.models;

import com.example.identityservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
