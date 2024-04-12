package com.example.identityservice.models;


import com.example.identityservice.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
