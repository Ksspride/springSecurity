package com.example.identityservice.models;

import com.example.identityservice.entity.Role;
import lombok.Data;

@Data
public class LoggedInUser {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
