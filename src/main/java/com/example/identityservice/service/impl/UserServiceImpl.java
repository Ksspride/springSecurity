package com.example.identityservice.service.impl;

import com.example.identityservice.entity.User;
import com.example.identityservice.models.UserRequest;
import com.example.identityservice.models.UserResponse;
import com.example.identityservice.repo.UserRepository;
import com.example.identityservice.service.UserService;
import com.example.identityservice.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${pride.default-user-password}")
    private String password;


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(()-> new IllegalArgumentException("Record not found"));
    }

    @Override
    public UserResponse createUser(UserRequest request) {
            User user = ObjectMapperUtils.map(request, User.class);
            user.setPassword(passwordEncoder.encode(password));
        return ObjectMapperUtils.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User savedUser = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not exists"));
        return ObjectMapperUtils.map(savedUser, UserResponse.class);
    }
}
