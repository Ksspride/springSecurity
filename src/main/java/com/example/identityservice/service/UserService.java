package com.example.identityservice.service;

import com.example.identityservice.entity.User;
import com.example.identityservice.models.UserRequest;
import com.example.identityservice.models.UserResponse;

public interface UserService {

    public User getUserByEmail(String email);
    public UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

}
